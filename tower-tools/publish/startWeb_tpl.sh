#!/bin/sh

prefix="$1"

app_home_dir="$(dirname $(pwd))"

jetty_pid=`/usr/sbin/lsof -n -P -t -i :$3`
[ -n "$jetty_pid" ] && kill $jetty_pid

sleep 3

nohup java -Dorg.eclipse.jetty.server.Request.maxFormContentSize=10485760 -Ddubbo.shutdown.hook=true -Dapp.home.dir=$app_home_dir -jar ../../jetty-runner.jar --port $3 --log /data1/logs/service/$prefix/requests.log-yyyy_mm_dd ../$prefix-web/target/$prefix-web.war > /dev/null  &