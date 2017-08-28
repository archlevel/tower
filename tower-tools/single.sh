#!/bin/bash

mkdir -p ../../projects/

cd ../../projects/

projectid=$1

##配置文件生成

cd ../soafw/soafw-config-maven-plugin/

projectjob=$projectid-job
if [ -n $2 ]; then
	projectjob=$projectjob-$2
fi

##config
mvn soafw-config:config -DartifactId=$projectid -DdestDir=../../projects -Dmodel=AllIn $2
