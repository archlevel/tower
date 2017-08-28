简介：
    Enterprise Audit Shell（EAS）是一个易用且强大的Unix shell行为审计开源系统，它可以详细记录并回放（replay）unix用户登录shell后所做的所有操作。软件包的作者是dhanks，不过遗憾的是自2006年以后EAS已不在更新。
下面介绍一下EAS的功能特点：

1、符合COBIT标准

2、ITIL的最佳技术实现

3、Unix主机使用的企业级审计视图（支持AIX、Solaris等）

4、符合SOX法案的企业级审计报告工具

5、支持审计报表并可定制

6、内置事务型数据传送，支持SQL92关系数据库，兼容ACID

7、支持负载均衡

8、支持审计数据备份/恢复

9、SSL加密传输

10、SSL公钥体系认证

11、文件传输、远程命令执行审计

12、用户默认shell可独立配置

13、审计日志支持数字签名防止篡改

14、C/S结构，客户端、服务器端可配置

15、会话超时机制

16、会话初始时显示企业策略信息

17、支持replay回放

18、支持远程登录（SSH、TELNET）、本地登录审计

19、可定制的事件通知（E-mail、短信等）

20、透明安装/使用，无需改变网络拓扑和使用习惯

EAS.zip 这里可以下载

http://blog.chinaunix.net/blog/downLoad/fileid/10119.html

yum install openssl openssl-devel -y
./configure
make make install

配置文件：/etc/eas/
生成KEY script:

点击(此处)折叠或打开

    #!/bin/bash
    OPENSSL=/usr/bin/openssl

    # Remove old files.
    rm -f index.txt*
    rm -f serial*
    rm -f *.pem
    rm -f *.key
    rm -rf CA

    # Create new CA directory
    mkdir CA
    mkdir CA/private
    mkdir CA/certs

    # Create the necesary files
    touch CA/index.txt
    echo 01 > CA/serial

    # Do the work
    cat banners/1 && $OPENSSL req -newkey rsa:1024 -keyout CA/private/cakey.pem -out CA/careq.pem -config conf/root.cnf
    cat banners/2 && $OPENSSL ca -config conf/root.cnf -out root.pem -days 1095 -batch -keyfile CA/private/cakey.pem -selfsign -infiles CA/careq.pem

    cat banners/3 && $OPENSSL req -newkey rsa:1024 -config conf/client.cnf -keyout client.pem -out newreq.pem -days 365
    cat banners/4 && $OPENSSL ca -config conf/client.cnf -batch -out newcert.pem -infiles newreq.pem
    cat banners/5 && $OPENSSL rsa < client.pem > client.key
    cat client.key newcert.pem > client.pem
    rm -f client.key
    rm -f newcert.pem
    rm -f newreq.pem

    cat banners/6 && $OPENSSL req -newkey rsa:1024 -config conf/server.cnf -keyout server.pem -out newreq.pem -days 365
    cat banners/7 && $OPENSSL ca -config conf/server.cnf -batch -out newcert.pem -infiles newreq.pem
    cat banners/5 && $OPENSSL rsa < server.pem > server.key
    cat server.key newcert.pem > server.pem
    rm -f server.key
    rm -f newcert.pem
    rm -f newreq.pem

