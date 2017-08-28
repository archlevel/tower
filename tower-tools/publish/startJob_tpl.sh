#!/bin/sh

app="$1"

prefix=""

app_home_dir="$(dirname $(pwd))"

tar -zxvf ../$prefix/$prefix-1.0-SNAPSHOT-bin.tar.gz

pid=`ps x | grep '$prefix-1.0-SNAPSHOT.jar' | grep -v grep | awk '{print $1}'`


echo "pid:"$pid

 [ -n "$pid" ] &&  kill $pid

sleep 3

nohup java -Ddubbo.shutdown.hook=true -Dapp.home.dir=$app_home_dir -jar $prefix-1.0-SNAPSHOT/lib/$prefix-1.0-SNAPSHOT.jar > /dev/null   &

new_pid=`ps x | grep $prefix-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $1}'`

echo "新的进程服务pid:"$new_pid