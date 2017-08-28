1，关于FastDFS

摘自：http://www.oschina.net/p/fastdfs 
FastDFS是一个开源的分布式文件系统，她对文件进行管理，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等等。

FastDFS服务端有两个角色：跟踪器（tracker）和存储节点（storage）。跟踪器主要做调度工作，在访问上起负载均衡的作用。

存储节点存储文件，完成文件管理的所有功能：存储、同步和提供存取接口，FastDFS同时对文件的meta data进行管理。所谓文件的meta data就是文件的相关属性，以键值对（key value pair）方式表示，如：width=1024，其中的key为width，value为1024。文件meta data是文件属性列表，可以包含多个键值对。

GitHub：https://github.com/happyfish100/fastdfs 
一个写的不错的博客：http://tech.uc.cn/?p=221

2，安装FastDFS

参考文档： 
https://github.com/happyfish100/fastdfs/blob/master/INSTALL

2.1，安装编译工具

yum -y groupinstall 'Development Tools' 
yum -y install wget 

2.2，安装libfastcommon类库

必须先按照这个，否则报错：

cc -Wall -D_FILE_OFFSET_BITS=64 -D_GNU_SOURCE -g -O -DDEBUG_FLAG -DOS_LINUX -DIOEVENT_USE_EPOLL -c -o ../common/fdfs_global.o ../common/fdfs_global.c  -I../common -I/usr/include/fastcommon
../common/fdfs_global.c:20:20: fatal error: logger.h: No such file or directory
 #include "logger.h"
                    ^
compilation terminated.
........

直接下载安装即可：

wget https://github.com/happyfish100/libfastcommon/archive/master.zip
unzip master.zip
cd libfastcommon-master
./make.sh
./make.sh install

2.3，安装FastDFS

wget  https://github.com/happyfish100/fastdfs/archive/V5.05.tar.gz
tar -zxvf V5.05.tar.gz 
cd fastdfs-5.05/
./make.sh
./make.sh install

2.4，启动FastDFS

安装之后配置文件在目录/etc/fdfs。

mv storage.conf.sample  storage.conf
mv tracker.conf.sample tracker.conf

首先修改配置文件： 
/etc/fdfs/tracker.conf，修改路径到/data/fdfs目录。

# the base path to store data and log files
base_path=/data/fdfs

启动： 
/usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf start

修改配置文件： 
/etc/fdfs/storage.conf，修改路径到/data/fdfs目录，同时配置tracker_server地址。

# the base path to store data and log files
base_path=/data/fdfs
# tracker_server can ocur more than once, and tracker_server format is
#  "host:port", host can be hostname or ip address
tracker_server=192.168.1.36:22122
# store_path#, based 0, if store_path0 not exists, it's value is base_path
# the paths must be exist
store_path0=/data/fdfs
#store_path1=/home/yuqing/fastdfs2

/usr/bin/fdfs_storaged /etc/fdfs/storage.conf start

启动的时候，看日志没有错误就说明启动成功。 
查看fdfs进程：

#ps -ef | grep fdfs
root     19880     1  0 21:42 ?        00:00:00 /usr/bin/fdfs_trackerd /etc/fdfs/tracker.conf start
root     20050     1  0 22:08 ?        00:00:00 /usr/bin/fdfs_storaged /etc/fdfs/storage.conf start
root     20060 19915  0 22:08 pts/1    00:00:00 grep --color=auto fdfs

2.5 安装nginx + lua + fastdfs module 
下载fastdfs-nginx-module模块 
https://github.com/happyfish100/fastdfs-nginx-module 
下载openresty nginx +lua包

wget https://github.com/happyfish100/fastdfs-nginx-module/archive/master.zip
unzip fastdfs-nginx-module-master.zip
wget http://openresty.org/download/ngx_openresty-1.7.10.1.tar.gz
tar -zxvf ngx_openresty-1.7.10.1.tar.gz

编译安装：这里注意下，需要增加参数。 
一个是stub_status统计模块，一个是realip模块。 
参考地址： 
http://blog.danielss.com/?p=80 
http://wiki.nginx.org/HttpRealipModuleChs

yum -y install pcre-devel openssl openssl-devel
cd ngx_openresty-1.7.10.1
./configure --with-luajit --with-http_stub_status_module --with-http_ssl_module --with-http_realip_module --add-module=/root/fastdfs-nginx-module-master/src 
gmake
gmake install

nginx +lua安装路径：

  nginx path prefix: "/usr/local/openresty/nginx"
  nginx binary file: "/usr/local/openresty/nginx/sbin/nginx"
  nginx configuration prefix: "/usr/local/openresty/nginx/conf"
  nginx configuration file: "/usr/local/openresty/nginx/conf/nginx.conf"
  nginx pid file: "/usr/local/openresty/nginx/logs/nginx.pid"
  nginx error log file: "/usr/local/openresty/nginx/logs/error.log"
  nginx http access log file: "/usr/local/openresty/nginx/logs/access.log"

启动，重启nginx

#启动
/usr/local/openresty/nginx/sbin/nginx
#重启
 /usr/local/openresty/nginx/sbin/nginx -s restart

3，测试FastDFS

配置服务：

cd /etc/fdfs

mv client.conf.sample client.conf

修改配置：

# the base path to store log files
base_path=/data/fdfs

# tracker_server can ocur more than once, and tracker_server format is
#  "host:port", host can be hostname or ip address
tracker_server=192.168.1.36:22122

配置nginx：

cp /root/fastdfs-nginx-module-master/src/mod_fastdfs.conf /etc/fdfs/

mkdir /data/ngx

修改mod_fastdfs.conf配置文件：修改tracker端口，否则nginx启动不了。路径也必须修改，否则找不到文件。

# the base path to store log files
base_path=/data/ngx
# FastDFS tracker_server can ocur more than once, and tracker_server format is
#  "host:port", host can be hostname or ip address
# valid only when load_fdfs_parameters_from_tracker is true
tracker_server=192.168.1.36:22122
# store_path#, based 0, if store_path0 not exists, it's value is base_path
# the paths must be exist
# must same as storage.conf
store_path0=/data/fdfs

拷贝配置文件，否则nginx启动不了，报错：

[2015-04-28 11:30:14] ERROR - file: ini_file_reader.c, line: 315, include file "http.conf" not exists, line: "#include http.conf"
[2015-04-28 11:30:14] ERROR - file: /root/fastdfs-nginx-module-master/src/common.c, line: 155, load conf file "/etc/fdfs/mod_fastdfs.conf" fail, ret code: 2
2015/04/28 11:30:14 [alert] 19969#0: worker process 2753 exited with fatal code 2 and cannot be respawned

解决办法：

cp /root/fastdfs-master/conf/http.conf /root/fastdfs-master/conf/mime.types  /etc/fdfs

nginx配置修改vi /usr/local/openresty/nginx/conf/nginx.conf

    server {
        listen       80;
        server_name  localhost;

        charset utf8;

        location /M00 {
                alias /data/fdfs/data;
                ngx_fastdfs_module;
        }
   ......
创建连接

ln -s /data/fdfs/data /data/fdfs/data/M00

重启nginx。 
测试命令：

echo "test fastdfs" > test.html
 /usr/bin/fdfs_test /etc/fdfs/client.conf upload test.html

上传成功，执行结果：


This is FastDFS client test program v5.05

Copyright (C) 2008, Happy Fish / YuQing

FastDFS may be copied only under the terms of the GNU General
Public License V3, which may be found in the FastDFS source kit.
Please visit the FastDFS Home Page http://www.csource.org/
for more detail.

[2015-04-25 18:06:09] DEBUG - base_path=/data/fdfs, connect_timeout=30, network_timeout=60, tracker_server_count=1, anti_steal_token=0, anti_steal_secret_key length=0, use_connection_pool=0, g_connection_pool_max_idle_time=3600s, use_storage_id=0, storage server id count: 0

tracker_query_storage_store_list_without_group:
        server 1. group_name=, ip_addr=192.168.1.36, port=23000

group_name=group1, ip_addr=192.168.1.36, port=23000
storage_upload_by_filename
group_name=group1, remote_filename=M00/00/00/wKgC1FU7ZxGAF19vAAAADcvBslg01.html
source ip address: 192.168.1.36
file timestamp=2015-04-25 18:06:09
file size=13
file crc32=3418468952
example file url: http://192.168.1.36/group1/M00/00/00/wKgC1FU7ZxGAF19vAAAADcvBslg01.html
storage_upload_slave_by_filename
group_name=group1, remote_filename=M00/00/00/wKgC1FU7ZxGAF19vAAAADcvBslg01_big.html
source ip address: 192.168.1.36
file timestamp=2015-04-25 18:06:09
file size=13
file crc32=3418468952
example file url: http://192.168.1.36/group1/M00/00/00/wKgC1FU7ZxGAF19vAAAADcvBslg01_big.html

测试访问：

curl http://192.168.1.36/M00/00/00/wKgC1FU7ZxGAF19vAAAADcvBslg01_big.html

如果返回“test fastdfs”说明配置成功。上面的每一步修改都要正确才可以。

查看nginx错误日志： 
tail -f /usr/local/openresty/nginx/logs/error.log

4，总结

FastDFS非常好的分布式文件系统。配置简单，可以多机器部署，同时官方给了一个Nginx模块，可以直接用nginx访问，非常方便。这样减少了中间的tomcat层，效率高。 
接下来继续研究lua的图片压缩。直接在nginx层进行图片压缩。效率更高，同时图片读取FastDFS的文件。