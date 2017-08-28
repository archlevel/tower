#!/bin/bash

######## 参数 sh tag.sh app、branch、version、source_base、app_source_path

if [ -z $2 ]; then
    branch="master"
else
    branch=$2
fi

#######该目录调整到你特定java代码工作空间；eg：/disk1/jenkins/jobs#######
source_base="$4"

app_source_path="$5"

cd $app_source_path
echo $(dirname $(pwd))
git tag -a $3 -m '$3'
git push origin $3