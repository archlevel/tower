#!/bin/bash

if [ -z $1 ];then
    echo "请重新输入项目名称"
    exit
fi
company=$2
if [ -z $2 ];then
    company="siling"
fi

mkdir -p ../../projects/

cd ../../projects/

projectid=$1

app_home_dir="$(pwd)"

if [ ! -d "$app_home_dir/$1" ]; then
	mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectid 
	cd $projectid
	rm -rf src
	echo parent build success
	#mac
	sed -i "" "s/<packaging>jar<\/packaging>/<packaging>pom<\/packaging>/g" pom.xml 
	#linux
	#sed -i  "s/<packaging>jar<\/packaging>/<packaging>pom<\/packaging>/g" pom.xml 
fi

cd $projectid

suffix=""

if [ -n "$3" ]; then
	suffix="-"$3
fi

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

projectwebid=$projectid-web
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectwebid -DarchetypeArtifactId=maven-archetype-webapp -Dcompany=$company
echo $projectwebid build success

##配置文件生成

cd ../tower/tower-config-maven-plugin

##config
mvn -B tower-config:config -DartifactId=$projectid -DdestDir=../../projects -Dmodel=AllIn -DgenModule=web -DmoduleSuffix=$3 -Dcompany=$company -X