DAO2.0缓存策略原理
概述：

由于DAO1.0只支持主键缓存，使得一些非主键查询没有使用到缓存,缓存覆盖率不高。为了缓解数据库压力，DAO1.0一般做法:

    先查出其主键ID，再根据主键ID查出值。(缺点：并没有减少数据库查询量，只是缓解拉数据库压力。)
    增加BLL缓存。(缺点：数据发生变化后，不能智能地清除缓存)

针对DAO1.0不足，DAO2.0在此基础上增加了外键缓存和表级缓存，可以缓存所有查询。有效的减缓数据库压力，提高缓存命中率。
样本表

loupan_basic表:
loupan_id 	loupan_name 	status
1 	test1 	0
2 	test2 	1
3 	test3 	1

house_types表
id 	loupan_id 	name
1 	1 	house1
2 	1 	house2
3 	2 	house3

cache_tags表
id 	tag 	updated 	updated_long
1 	loupan_basic 	1 	2
2 	house_types 	3 	4
概念解释

    主键缓存(pk):以表的主键为缓存key缓存该行数据。例如：loupan_basic表以loupan_id为缓存key。
    外键缓存(main_columns):house_types表中的loupan_id为外键,以loupan_id缓存house_types中多条数据缓存。
    表级缓存(查询不支持上面两种缓存策略时使用):查询条件中不包含主键和外键的查询缓存。例如:loupan_basic表status=1的结果集。
    cache_tags表:纪录每一张表的主键,外键和表的最新版本信息
    cache_tags表缓存:缓存每张表的版本信息，缓存时间300秒。
    updated版本号:该表的版本信息,当表发生改变(insert,replace,update)该版本号改变。(updated版本号发生变化，涉及到该表的表级缓存都会失效。)
    updated_long版本号:主键缓存和外键缓存的版本信息。(updated_long版本号发生变化,涉及到该表的主键缓存和外键缓存都会失效)
    >updated_long版本号一般很少改变。1:job直接更新数据库后，改变updatd_long版本号使缓存失效，2:程序中更新数据，需要删除大量的主键缓存和外键缓存时，改变该值。

三种缓存策略
主键缓存(pk)u

应用场景:根据主键查询时使用。
* 查询执行步骤:
1. select * from loupan_basic where loupan_id=?;
2. 是否存在缓存，mc缓存key拼接规则{表名}+{updated_long版本号}+{主键ID}, 如果存在直接返回结果，否则查询数据库，设置缓存并返回结果。例如：loupan_basic表主键mc缓存key:loupan_basic##2##1
* 更新执行步骤:
1. update loupan_basic set loupan_name=? where loupan_id=?;
2. 删除外键缓存。(如何删除外键缓存，下文有说明)
3. 删除对应的主键缓存
4. 更新updated版本号，删除cache_tags表缓存

外键缓存(main_columns)

应用场景:根据外键查询
* 查询执行步骤:
1. select * from house_types where loupan_id=?;
2. 拼接mc缓存key，{表名}+{updated_long版本号}+{查询条件参数}。ex:house_types##4##{loupan_id=?}
3. 将mc缓存key纪录到redis中。redis key:{表名}+{外键名}+{外键值}+{updated_long版本号}
4. 判断是否存在mc缓存key,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。
* 更新执行步骤:
1. update house_types where loupan_id=?;
2. 找出house_types对应的主键缓存,删除主键缓存。
>主键缓存数量超过threshold_for_delete_pk_by_where＝100值时，更新updated_long版本号
3. 删除外键缓存。
>*查出redis中对应的外键缓存。(set结构，其中的值对应mc中的key)
>*删除mckey对应的值。
4. 更新updated版本号，删除cache_tags表缓存

表级缓存

应用场景:查询既不是主键查询，也不是外键查询的查询，使用该缓存。
* 查询执行步骤:
1. select * from loupan_basic where status=?;
2. 拼接mc缓存key, {表名}+{update版本号}+{查询条件参数}
3. 是否存在该缓存,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。
* 更新执行步骤：
改变updated版本号
外键缓存优化

功能:对于有些外键查询实现一次查询，分开缓存。由于分开缓存，所以只能满足一些特殊查询。
条件:
1. 外键用in的查询
2. 查询结果不排序,也就是查询语句中无order。
3. 查询无limit和offset限制。

例如：

select  * from house_types where loupan_id in ( 1,2);
可以拆分成两个查询:
select * from house_types where loupan_id = 1;
select * from house_types where loupan_id = 2;

疑问

    现在更新cache_tags的方式是:

$update_count = $update_count + rand(1, 10);
$sql = "update cache_tags set updated=unix_timestamp() + $update_count where tag IN ($tag_ss)";

当并发时cache_tags版本号是否存在回退的可能? 是否可以使用

update cache_tags set `updated`=`updated`+1;

代替。
2. 外键查询时步骤优化：
是否可以将步骤

    4. 判断是否存在mc缓存key,如果存在直接返回结果，否则查询数据设置mc缓存，返回结果。

放到第2步

#########

我觉得这里写的更多的是具体的实现，策略应该更简单一点，我试着写了下：
DAO1.0缓存策略
缓存类型

所有缓存均以表为scope（这个scope该怎么更好的说明呢？）

    主键缓存，通过主键缓存单行数据；
    条件缓存，通过查询条件缓存多行数据。

缓存的更新和过期

    直接通过主键更新或者严格按照原条件去更新数据时，才会去更新缓存。
    由于更新策略并不完善，所以通常会设一个非常短的过期时间。

存在的问题

    缓存内容不准确；
    缓存命中率低；
    不同应用缓存内容不同。

DAO2.0缓存策略
缓存类型

所有缓存均以表为scope

    主键缓存，通过主键缓存单行数据；
    条件缓存，通过查询条件缓存多行数据，查询条件中不含主键或外键条件；
    外键缓存，通过含有外键条件的查询条件缓存多行数据；
    外键缓存集，某一外键值对应所有的外间缓存集合。

缓存的更新和过期

    更新任意数据时，过期所有条件缓存
    按主键更新数据时，过期对应主键缓存
    按条件更新数据，同时条件中含有外键时，可能有两种情况：
        被更新的数据较少时，过期对应主键缓存，并分别过期对应外键缓存集中的外键缓存
        被更新的数据较多时，过期所有主键缓存和外键缓存