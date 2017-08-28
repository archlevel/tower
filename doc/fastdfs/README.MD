FastDFS安装配置手册
文件服务器分布式系统安装手册

本文档详细的介绍了FastDFS的最小集群安装过程。集群环境如下：

tracker：20.2.64.133 。用于调度工作，在访问上起负载均衡的作用。

group1:

      storage1: 20.10.129.150。存储节点存储文件，完成文件管理的所有功能。

      storage2: 20.12.1.73。

group2:

      storage2:20.12.1.74

   以下将详细的介绍此分布式的安装过程，针对不同的系统，安装过程中部分细节（存储路径设置等）可能稍有不同，但基本步骤相同。

第一步安装libevent
在搭建分布式环境之前，需要确保已经安装了libevent，FastDFS安装时对libevent的版本有一定要求。注需要卸载重装系统自带的libevent。使用最新的stable版本。本示例中使用的版本为libevent-2.0.21-stable。注意，此步骤需要在所有机器上都执行，否则会导致FastDFS安装失败。

1：判断是否安装了libevent

>> rpm -qa|grep libevent

如果有输出，则说明已经安装了libevent

Fastdfs对libevent版本有要求，所以先删除系统自带的libevent,然后安装最新稳定版本

在本示例中，输出为libevent-1.4.13-1.el6.x86_64

2：卸载已有的libevent

  >> rpm -e libevent --nodeps

3：安装最新的libevent

wget https://github.com/downloads/libevent/libevent/libevent-2.0.21-stable.tar.gz

本示例的libevent使用版本为libevent-2.0.21-stable.tar.gz

安装libevent   

>> tar zvxf libevent-2.0.21-stable.tar.gz

>> cd libevent-2.0.21-stable

>> ./configure --prefix=/usr 

>> make

>>make install

4：检查是否安装成功

>> ls –al /usr/lib |grep libevent（或者>> ls –al /usr/local/lib |grep libevent）若有结果，则说明安装正确

注意：

  1) 如果是在32为系统上安装，且libevent 的安装目录在/usr/local/lib下，则还需要建立libevent-2.0.so.5 到/usr/lib的软链接，这样其他程序运行时才可以找到libevent库

>> ln –s /usr/local/lib/libevent-2.0.so.5 /usr/lib/libevent-2.0.so.5

 2) 如果是在64位系统，需要创建一个libevent-2.0.so.5 到/usr/lib64的软链接

>> ln -s /usr/lib/libevent-2.0.so.5 /usr/lib64/libevent-2.0.so.5 (假设libevent 的安装目录在/usr/lib下)

到此一步，安装libevent工作完毕。

 

第二步安装FastDFS
本示例中使用的FastDFS版本为4.0.6。

安装FastDFS

>> tar zvxf FastDFS_v4.06.tar.gz

>> cd FastDFs

>> ./make.sh

>> ./make.sh install

注意上面两步，检查是否出错，如果出错，则说明上面的libevent没有安装好

安装成功后，FastDFS 安装在/usr/local/bin中。配置文件在/etc/fdfs中

至此，FastDFS安装完毕

此步骤需要在所有机器上都执行。

第三步安装nginx以及fastdfs-nginx-module（只需在storage）
FastDFS通过HTTP服务器来提供HTTP服务。为了支持高并发的访问，以及提供负载均衡等高性能的服务，本示例使用nginx作为HTTP服务器，FastDFS为我们提供了Nginx上使用的FastDFS模块（fastdfs-nginx-module）。此步操作只需要在storage上执行。tracker

上可不执行。

1：fastdfs-nginx-module安装：

>>  tar -zxvf fastdfs-nginx-module_v1.11.tar.gz    (解压后的路径为/home/chenfeic/fastdfs/fastdfs-nginx-module)

Nginx需要依赖第三方的库 nginx的gzip模块需要zlib库，rewrite模块需要pcre库，那么我们来依次安装它们。

2：zlib库安装:

>> tar -vxzf zlib-1.2.8.tar.gz    （本示例中解压位置为/home/chenfeic/fastdfs/zlib-1.2.8）

>>cd zlib-1.2.8

>> ./configure --prefix=/usr/local/zlib   （设置安装路径）

>> make

>> make install

3：pcre库安装：

>> tar -vxzf pcre-8.33.tar.gz  （本示例中解压位置为--with-pcre= /home/chenfeic/ fastdfs / pcre-8.33 ）

>> cd prce-8.33

>> ./configure --prefix=/usr/local/pcre

--libdir=/usr/local/lib/pcre --includedir=/usr/local/include/pcre

>> make

>> make install

4：nginx安装：

>> tar -zxvf nginx-1.4.4.tar.gz

>> cd nginx-1.4.4

>>./configure --prefix=/usr/local/nginx --with-zlib=/home/chenfeic/fastdfs/zlib-1.2.8  --with-pcre=/home/chenfeic/fastdfs/pcre-8.33 --sbin-path=/usr/local/nginx --add-module=/home/chenfeic/fastdfs/fastdfs-nginx-module/src

>> make

>> make install 

注意：--with-pcre --with-zlib 后面的路径都是源码路径，而不是编译后的包。在实际的过程中，此路径根据具体情况填写。

至此，我们的nginx安装成功，nginx默认是80端口，为了防止与其他服务冲突，建议换成其他端口（后续章节有详细介绍）。

此步骤需要在所有storage节点上都执行。（tracker节点不需要执行）

 

第四步配置及启动FastDFS
1：tracker配置(只需在跟踪器节点配置)

修改tracker的配置文件tracker.conf。该文件位置在/etc/fdfs/tracker.conf。主要修改以下两处，如有其他调整，可参考FastDFS文档自行调整。

>> /etc/fdfs/tracker.conf
    base_path=/home/chenfeic/fastdfs/tracker    ====>放置data和log的目录。这个路径必须存在。

##include http.conf 修改为#include http.conf    ====> 这里一定要注意！是 #include，不是include!!! 如果没有此配置项，则不需要处理

 

启动tracker

>> /usr/local/bin/fdfs_tracker /etc/fdfs/tracker.conf

检查是否正常启动

 >> netstat -anp|grep -w fdfs

看是否有输出，且22122端口已启动

 

2：storage配置（只需在存储节点配置，同一group节点的配置是一样的）

1）修改nginx端口（可选）

 nginx 默认端口号为80，为了防止冲突，可以设置为其他端口，本示例中将其修改为8080。  

修改/usr/local/nginx/conf/nginx.conf

>> vim /usr/local/nginx/conf/nginx.conf

   Server {
           listen 8080(从80改为8080)

}

2）支持FastDFS模块

在nginx的server配置段中增加 /group1/M00的location声明

storage $> vim /usr/local/nginx/conf/nginx.conf

location /group1 /M00 {  //注意：此处如果是在group1就写成group1/M00。如果是group2节点就写group2/M00。依次类推。

root  /home/chenfeic/ fastdfs /storage/data; //必须保证路径存在，若不存在就创建

ngx_fastdfs_module;  注意这两行都有分号

}（其中/home/chenfeic/ fastdfs /storage/为storage中配置的store_path0路径，data目录一定要加上）

 给 storage 的存储目录做一个软连接

> ln -s /home/chenfeic/ fastdfs /storage/data  /home/chenfeic/ fastdfs /storage /data /M00

3）修改storage配置

修改storage的配置文件storage.conf。该文件位置在/etc/fdfs/storage.conf。主要修改以下几如有其他调整，可参考FastDFS文档自行调整

    group_name=group1 ====> 此台storage server所属的服务器组名。此处如果是在group1就写成group1。如果是group2节点就写group2。依次类推。

 

    base_path=/home/chenfeic/ fastdfs /storage ====> 放置data和log的目录。此路径必须保证存在。

    store_path0=/home/chenfeic/ fastdfs /storage       ====> 放置文件的目录，此路径必须保证存在,建议跟base_path保持一致（/home/chenfeic/ fastdfs /storage与nginx server段中的设置保持一致,）

    tracker_server=20.2.64.133:22122         ====> tracker server的ip和端口，此处可以写多个tracker server，每行一个，因为本示例中只有一个tracker，所以只需写一个。

    http.disabled=true                    ====> 关闭内置的web server，如果有此配置项就设置，否则就不用设置。

    http.server_port=8080                    ====> web server的端口改成8080（与nginx 端口一致）

 

4）修改fastdfs-nginx-module配置

拷贝mod_fastdfs.conf 到/etc/fdfs目录下

 >>cp /home/chenfeic/fastdfs/fastdfs-nginx-module/src/mod_fastdfs.conf  /etc/fdfs/

 

5）修改mod_fastdfs.conf配置文件

>> vim /etc/fdfs/mod_fastdfs.conf

    base_path=/home/chenfeic/ fastdfs /storage        ====> 放置log的目录,与storage中的配置保持一致。

    tracker_server=20.2.64.133:22122         ====> tracker server的ip和端口，此处可以写多个tracker server，每行一个，因为本示例中只有一个tracker，所以只需写一个。

    group_name=group1                     ====> 此台storage server所属的服务器组名。此处如果是在group1就写成group1。如果是group2节点就写group2。依次类推。

 

    url_have_group_name = true             ====> 在URL中包含group名称。一定要设置true

    store_path0=/home/chenfeic/ fastdfs /storage            ====> 放置文件的目录，与storage中保持一致

response_mode=proxy                ====> 对文件同步延迟的处理方式，通过redirect跳转和proxy代理两种方式解决

group_count=2       ====>group的个数，在本示例中有两个group,所以设为2

 

 

到此，配置已经完成，启动nginx和相关storage

  >>/usr/local/nginx/nginx

>> /usr/local/bin/fdfs_storage /etc/fdfs/ storage.conf

检查是否正常启动

 >> netstat –anp|grep –w 8080  看8080端口是否启动

 

3：测试是否正常安装

    此步骤在tracker或者storage都可以测试，本示例中在storage上测试，修改client.conf配置文件。

    > vim /etc/fdfs/client.conf

base_path=/home/chenfeic/ fastdfs

tracker_server=20.2.64.133:22122

测试：

     >vim hello.txt (创建一个测试的文件)

> /usr/local/bin/fdfs_test / etc/fdfs/client.conf  upload  hello.txt

查看结果，看文件是否正确上传

 

 

可以在任何storage机器上执行如下命令，查看fastdfs集群状态，如果各个storage状态都为ACTIVE，则搭建成功。

/usr/local/bin/fdfs_monitor /etc/fdfs/storage.conf

 

关闭tracker(storage)

  /usr/local/bin/stop.sh /usr/local/bin/fdfs_trackered(fdfs_storaged) /etc/fdfs/tracker.conf（storage.conf）

  或者 killall fdfs_trackered(storaged)  -------注意，千万不要使用-9强行杀死进程。否则可能会导致binlog数据丢失的问题。

 重启tracker(storage)

/usr/local/bin/restart.sh /usr/local/bin/fdfs_trackered(fdfs_storaged) /etc/fdfs/tracker.conf（storage.conf）

删除storage

 /usr/local/bin/fdfs_monitor /etc/fdfs/storage.conf delete group2 20.12.1.73(删除group2中ip为20.12.1.73的storage)

注意：在配置过程，要注意防火墙的设置。关闭防火墙，或者设置对相关端口例外。