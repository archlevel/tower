#!/bin/bash

if [ -z $1 ];then
	echo "请重新输入： 项目名 公司名"
  	exit
fi
company=$2
if [ -z $2 ];then
    company="siling"
fi

mkdir -p ../../projects/

cd ../../projects/

projectid=$1
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectid 
cd $projectid
rm -rf src
echo parent build success
#mac
sed -i "" "s/<packaging>jar<\/packaging>/<packaging>pom<\/packaging>/g" pom.xml 
#linux
#sed -i  "s/<packaging>jar<\/packaging>/<packaging>pom<\/packaging>/g" pom.xml 

if [ ! -d "$app_home_dir/$1/$1-util" ]; then
	projectcommon=$projectid-util
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectcommon -Dcompany=$company
	echo $projectcommon build success
fi

#if [ ! -d "$app_home_dir/$1/$1-config" ]; then
#	projectconfig=$projectid-config
#	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectconfig -Dcompany=$company
#	echo $projectconfig build success
#fi

if [ ! -d "$app_home_dir/$1/$1-cache" ]; then
	projectcache=$projectid-cache
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectcache -Dcompany=$company
	echo $projectcache build success
fi

if [ ! -d "$app_home_dir/$1/$1-dao" ]; then
	projectdao=$projectid-dao
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectdao -Dcompany=$company
	echo $projectdao build success
fi

if [ ! -d "$app_home_dir/$1/$1-rpc" ]; then
	projectrpc=$projectid-rpc
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectrpc -Dcompany=$company
	echo $projectrpc build success
fi

if [ ! -d "$app_home_dir/$1/$1-mq" ]; then
	projectmq=$projectid-mq
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectmq -Dcompany=$company
	echo $projectmq build success
fi

if [ ! -d "$app_home_dir/$1/$1-domain" ]; then
	projectdomain=$projectid-domain
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectdomain -Dcompany=$company
	echo $projectdomain build success
fi

if [ ! -d "$app_home_dir/$1/$1-service" ]; then
	projectservice=$projectid-service
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectservice -Dcompany=$company
	echo $projectservice build success
fi

projectservice=$projectid-service-impl
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectservice -Dcompany=$company
echo $projectservice build success

##配置文件生成

cd ../tower/tower-config-maven-plugin

##config
mvn -B tower-config:config -DartifactId=$projectid -DdestDir=../../projects -Dmodel=AllIn -DgenModule=service -DmoduleSuffix=$3 -Dcompany=$company -X

cd ../../soafw/tools/

sh ./sed-pom.sh $1