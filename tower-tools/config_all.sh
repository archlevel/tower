#!/bin/bash

if [ -z $1 ];then
    echo "请重新输入： 项目名 公司名 【模块后缀 for job】"
    exit
fi
company=$2
if [ -z $2 ];then
    company="siling"
fi

tools="`pwd`"

mkdir -p ../../projects/

cd ../../projects/

projectid=$1

##配置文件生成

echo "`pwd`"

cd ../tower/tower-config-maven-plugin

##config
mvn -B com.tower.soafw:tower-config-maven-plugin:1.0.0-SNAPSHOT:config -DartifactId=$projectid -DdestDir=../../projects -Dmodel=all -DmoduleSuffix=$3 -Dcompany=$company -Dscop=all -X
