由于数据量的巨大，大部分Web应用都需要部署很多个数据库实例。这样，有些用户操作就可能需要去修改多个数据库实例中的数据。传统的解决方法是使用分布式事务保证数据的全局一致性，经典的方法是使用两阶段提交协议。

长期以来，分布式事务提供的优雅的全局ACID保证麻醉了应用开发者的心灵，很多人都不敢越雷池一步，想像没有分布式事务的世界会是怎样。如今就如MySQL和PostgreSQL这类面向低端用户的开源数据库都支持分布式事务了，开发者更是沉醉其中，不去考虑分布式事务是否给系统带来了伤害。

事实上，有所得必有所失，分布式事务提供的ACID保证是以损害系统的可用性、性能与可伸缩性为代价的。只有在参与分布式事务的各个数据库实例都能够正常工作的前提下，分布式事务才能够顺利完成，只要有一个工作不正常，整个事务就不能完成。这样，系统的可用性就相当于参加分布式事务的各实例的可用性之积，实例越多，可用性下降越明显。从性能和可伸缩性角度看，首先是事务的总持续时间通常是各实例操作时间之和，因为一个事务中的各个操作通常是顺序执行的，这样事务的响应时间就会增加很多；其次是一般Web应用的事务都不大，单机操作时间也就几毫秒甚至不到1毫秒，一但涉及到分布式事务，提交时节点间的网络通信往返过程也为毫秒级别，对事务响应时间的影响也不可忽视。由于事务持续时间延长，事务对相关资源的锁定时间也相应增加，从而可能严重增加了并发冲突，影响到系统吞吐率和可伸缩性。

正是由于分布式事务有以上问题，eBay在设计上就不采用分布式事务，而是通过其它途径来解决数据一致性问题。其中使用的最重要的技术就是消息队列和消息应用状态表。

举个例子。假设系统中有以下两个表

```
user(id, name, amt_sold, amt_bought)
transaction(xid, seller_id, buyer_id, amount)
```
其中user表记录用户交易汇总信息，transaction表记录每个交易的详细信息。

这样，在进行一笔交易时，若使用事务，就需要对数据库进行以下操作：

```
begin;
INSERT INTO transaction VALUES(xid, $seller_id, $buyer_id, $amount);
UPDATE user SET amt_sold = amt_sold + $amount WHERE id = $seller_id;
UPDATE user SET amt_bought = amt_bought + $amount WHERE id = $buyer_id;
commit;
```

即在transaction表中记录交易信息，然后更新卖家和买家的状态。

假设transaction表和user表存储在不同的节点上，那么上述事务就是一个分布式事务。要消除这一分布式事务，将它拆分成两个子事务，一个更新transaction表，一个更新user表是不行的，因为有可能transaction表更新成功后，更新user失败，系统将不能恢复到一致状态。

解决方案是使用消息队列。如下所示，先启动一个事务，更新transaction表后，并不直接去更新user表，而是将要对user表进行的更新插入到消息队列中。另外有一个异步任务轮询队列内容进行处理。

```
begin;
INSERT INTO transaction VALUES(xid, $seller_id, $buyer_id, $amount);
put_to_queue “update user(“seller”, $seller_id, amount);
put_to_queue “update user(“buyer”, $buyer_id, amount);
commit;
for each message in queue
begin;
dequeue message;
if message.type = “seller” then
UPDATE user SET amt_sold = amt_sold + message.amount WHERE id = message.user_id;
else
UPDATE user SET amt_bought = amt_bought + message.amount WHERE id = message.user_id;
end
commit;
end
```
上述解决方案看似完美，实际上还没有解决分布式问题。为了使第一个事务不涉及分布式操作，消息队列必须与transaction表使用同一套存储资源，但为了使第二个事务是本地的，消息队列存储又必须与user表在一起。这两者是不可能同时满足的。

如果消息具有操作幂等性，也就是一个消息被应用多次与应用一次产生的效果是一样的话，上述问题是很好解决的，只要将消息队列放到transaction表一起，然后在第二个事务中，先应用消息，再从消息队列中删除。由于消息队列存储与user表不在一起，应用消息后，可能还没来得及将应用过的消息从队列中删除时系统就出故障了。这时系统恢复后会重新应用一次这一消息，由于幂等性，应用多次也能产生正确的结果。

但实际情况下，消息很难具有幂等性，比如上述的UPDATE操作，执行一次和执行多次的结束显然是不一样的。解决这一问题的方法是使用另一个表记录已经被成功应用的消息，并且这个表使用与user表相同的存储。假设增加以下表 

```
message_applied(msg_id)记录被成功应用的消息，则产生最终的解决方案如下：
begin;
INSERT INTO transaction VALUES(xid, $seller_id, $buyer_id, $amount);
put_to_queue “update user(“seller”, $seller_id, amount);
put_to_queue “update user(“buyer”, $buyer_id, amount);
commit;
for each message in queue
begin;
SELECT count(*) as cnt FROM message_applied WHERE msg_id = message.id;
if cnt = 0 then
if message.type = “seller” then
UPDATE user SET amt_sold = amt_sold + message.amount WHERE id = message.user_id;
else
UPDATE user SET amt_bought = amt_bought + message.amount WHERE id = message.user_id;
end
INSERT INTO message_applied VALUES(message.id);
end
commit;
if 上述事务成功
dequeue message
DELETE FROM message_applied WHERE msg_id = message.id;
end
end
```
我们来仔细分析一下：
1、消息队列与transaction使用同一实例，因此第一个事务不涉及分布式操作；
2、message_applied与user表在同一个实例中，也能保证一致性；
3、第二个事务结束后，dequeue message之前系统可能出故障，出故障后系统会重新从消息队列中取出这一消息，但通过message_applied表可以检查出来这一消息已经被应用过，跳过这一消息实现正确的行为；
4、最后将已经成功应用，且已经从消息队列中删除的消息从message_applied表中删除，可以将message_applied表保证在很小的状态（不清除也是可以的，不影响系统正确性）。由于消息队列与message_applied在不同实例上，dequeue message之后，将对应message_applied记录删除之前可能出故障。一但这时出现故障，message_applied表中会留下一些垃圾内容，但不影响系统正确性，另外这些垃圾内容也是可以正确清理的。

虽然由于没有分布式事务的强一致性保证，使用上述方案在系统发生故障时，系统将短时间内处于不一致状态。但基于消息队列和消息应用状态表，最终可以将系统恢复到一致。使用消息队列方案，解除了两个数据库实例之间的紧密耦合，其性能和可伸缩性是分布式事务不可比拟的。

当然，使用分布式事务有助于简化应用开发，使用消息队列明显需要更多的工作量，两者各有优缺点。个人观点是，对于时间紧迫或者对性能要求不高的系统，应采用分布式事务加快开发效率，对于时间需求不是很紧，对性能要求很高的系统，应考虑使用消息队列方案。对于原使用分布式事务，且系统已趋于稳定，性能要求高的系统，则可以使用消息队列方案进行重构来优化性能。

