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

projectcommon=$projectid-util
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectcommon -Dcompany=$company
echo $projectcommon build success

#projectconfig=$projectid-config
#mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectconfig -Dcompany=$company
#cho $projectconfig build success

projectcache=$projectid-cache
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectcache -Dcompany=$company
echo $projectcache build success

projectdao=$projectid-dao
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectdao -Dcompany=$company
echo $projectdao build success

projectrpc=$projectid-rpc
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectrpc -Dcompany=$company
echo $projectrpc build success

projectmq=$projectid-mq
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectmq -Dcompany=$company
echo $projectmq build success

projectdomain=$projectid-domain
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectdomain -Dcompany=$company
echo $projectdomain build success

projectservice=$projectid-service
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectservice -Dcompany=$company
echo $projectservice build success

projectjob=$projectid-job
if [ -n "$3" ]; then
	projectjob=$projectjob-$3
fi

mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectjob -Dcompany=$company
echo $projectjob build success

projectservice=$projectid-service-impl
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectservice -Dcompany=$company
echo $projectservice build success

projectwebid=$projectid-web
mvn -B archetype:generate -DarchetypeCatalog=locale -DgroupId=com.$company.service.$projectid -DartifactId=$projectwebid -DarchetypeArtifactId=maven-archetype-webapp -Dcompany=$company
echo $projectwebid build success

##配置文件生成

cd ../tower/tower-config-maven-plugin

##config
mvn -B tower-config:config -DartifactId=$projectid -DdestDir=../../projects -Dmodel=AllIn -DmoduleSuffix=$3 -Dcompany=$company -X
