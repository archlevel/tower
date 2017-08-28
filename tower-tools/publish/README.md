[root@test ~]# ssh-keygen

Generating public/private rsa key pair.

Enter file in which to save the key (/root/.ssh/id_rsa):

Enter passphrase (empty for no passphrase):

Enter same passphrase again:

Your identification has been saved in /root/.ssh/id_rsa.

Your public key has been saved in /root/.ssh/id_rsa.pub.

The key fingerprint is:
fc:c9:fe:e9:a0:d4:30:96:28:0a:f9:c6:e4:8d:ec:0a root@test.opsers.org

[root@test ~]# ssh-copy-id -i .ssh/id_rsa.pub 192.168.6.10

root@192.168.6.10′s password:

Now try logging into the machine, with “ssh ’192.168.6.10′”, and check in:
.ssh/authorized_keys
to make sure we haven’t added extra keys that you weren’t expecting.

确定上传来的文件是存在的

[root@test ~]# ssh root@192.168.6.10

root@192.168.6.10′s password:

Last login: Mon Apr 25 14:00:51 2011 from 192.168.6.19
[root@yufei ~]# ls -l .ssh/authorized_keys
-rw——-. 1 root root 402  4月 25 14:06 .ssh/authorized_keys

[root@yufei ~]#

说明文件上传成功

配置sshd_config

[root@yufei ~]# cd /etc/ssh/

[root@yufei ssh]# cp sshd_config sshd_config.bak

[root@yufei ssh]# vim sshd_config

把下面的两行注释去掉

PubkeyAuthentication yes

AuthorizedKeysFile .ssh/authorized_keys

保存后，重新启动sshd服务

[root@yufei ssh]# service sshd restart

[root@yufei ssh]# exit

logout
Connection to 192.168.6.10 closed.
退出RHEL6系统，回到RHEL5环境中

[root@test ~]# ssh root@192.168.6.10

Last login: Mon Apr 25 14:07:29 2011 from 192.168.6.19

[root@yufei ~]#

已经无需输入密码直接登陆了