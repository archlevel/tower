#!/bin/sh

prefix="$1"

app_home_dir="$(dirname $(pwd))"

tar -zxvf ../$prefix-service-impl/$prefix-service-impl-1.0-SNAPSHOT-bin.tar.gz

pid=`ps x | grep $prefix-service-impl-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $1}'`

echo "pid:"$pid

 [ -n "$pid" ] && kill $pid

sleep 3

nohup java -Ddubbo.shutdown.hook=true -Dapp.home.dir=$app_home_dir -jar $prefix-service-impl-1.0-SNAPSHOT/lib/$prefix-service-impl-1.0-SNAPSHOT.jar >/dev/null  &