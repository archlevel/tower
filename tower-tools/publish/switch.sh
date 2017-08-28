#!/bin/sh

#首先判断版本号文件是否已存在 如果不存在则创建,存在则备份 并更新当前文件内容

 echo "输入的参数:"$1" "$2" "$3" "$4

 org_file=$1/$2/$3/$2"_"$3"_seq.txt"

 version_file=$1/$2/$3/$2"_"$3"_last_version.txt"

 version_file_old=$1/$2/$3/$2"_"$3"_last_version_bak.txt"

 ##########保存版本所属的日期

 current_date=`date +%Y-%m-%d`

 date_file=$1/$2/$3/$1"_"$2"_date_version.txt"


 if [ $# != 4 ];then

  echo "参数数量不是4个,发布路径、项目名称、版本号、分支名称"

  exit

fi

 if [ ! -f "$org_file" ]; then

   touch $org_file
   echo 1 > $org_file

 fi

 if [ ! -f "$version_file" ]; then

   touch $version_file
   echo $4 > $version_file

 fi

 if [ ! -f "$version_file_old" ]; then

   touch $version_file_old
   echo $4 > $version_file_old

 fi

  echo "当前准备同步的版本号:"$4

 while read oldversion
  do
  echo "发布前的最后版本版本号:"$oldversion
  echo $oldversion > $version_file_old

 done < $version_file

 echo $4 > $version_file

 echo "switch backup success#######"

 #修改当前的软连接设置
 cd $1/$2/$3
 rm -rf current
 echo "当前目录:$1/$2/$3/$4/bin"
 ln -s $4/bin current