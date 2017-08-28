### 下载
wget https://download.elastic.co/logstash/logstash/logstash-2.2.2.tar.gz
### 参考文档
https://www.elastic.co/guide/en/logstash/current/index.html
### 安装
+ 备注：不能在root用户下运行
+ 新增一个用户
+ mv elasticsearch-2.2.1.tar.gz /opt/
+ cd /opt/
+ tar -zxvf elasticsearch-2.2.1.tar.gz
+ cd elasticsearch-2.2.1/bin
+ chmod u+x elasticsearch
+ vim ../config/elasticsearch.yml 找到network.host，打开备注同时设置network.host=0.0.0.0,不然不能远程访问，默认只能本地（curl http://127.0.0.1:9200）,保存退出
+ 启动 nohup ./elasticsearch >/dev/null &