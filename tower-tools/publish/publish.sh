#!/bin/bash

######## 参数1表示项目名称

if [ "$1" != "oft" ]&&[ "$1" != "b2b" ]&&[ "$1" != "oapi" ];then

echo "不是有效的项目名称oft|b2b||oapi请重新输入"

exit

fi

if [ -z $2 ]; then
    branch="master"
else
    branch=$2
fi

#######该目录调整到你特定java代码工作空间；eg：/disk1/jenkins/jobs#######
source_base="/disk1/jenkins/jobs"
#######该目录调整为程序发布目录；eg：/disk1/apps#######
release_base="/disk1/apps"
#######maven 配置文件#######
mvn_config="/disk1/apache-maven-3.3.3/conf/settings_leya.xml"

app_source_path="$source_base/$1_$branch"

app_release_path="$release_base/$1/$branch"

shell_gen_base="$app_release_path/temp"

mkdir -p "$shell_gen_base"

#seq_no="1"
#next_seq_no="2"
date_time=`date +%Y-%m-%d`

#######log_path#######
log_path="/data1/logs/service/$1"

global_config_path="/config"

#######初始化版本序号#######
tpl_file=$app_release_path"/"$1"_"$2"_seq.txt"

date_file=$app_release_path"/"$1"_"$2"_date_version.txt"

if [ ! -f "$date_file" ]; then

touch $date_file
echo $date_time  > $date_file

fi

if [ ! -f "$tpl_file" ]; then

touch $tpl_file
echo 1 > $tpl_file

fi
#######读取最后版本#######
while read old_date
do
echo "发布前的最后日期:"$old_date

if  [ "$date_time" != "$old_date"  ];then

echo 1 > $tpl_file

echo $date_time  > $date_file
fi
done < $date_file

while read seq_num
do
echo "LINE:"$seq_num
seq_no=$seq_num
done < $tpl_file

echo "当前版本号:""$date_time""_"$seq_no

current_version="$date_time""_"$seq_no

next_seq_no=$[ $seq_no + 1 ]

echo "下一个序列号next_seq_no:"$next_seq_no

sed -i 's'/$seq_no/$next_seq_no/'g' $tpl_file

global_pom_path="$app_release_path"/"$date_time""_"$seq_no

web_path="$app_release_path"/"$date_time""_"$seq_no/"$1-web"

job_path="$app_release_path"/"$date_time""_"$seq_no/

service_impl_path="$app_release_path"/"$date_time""_"$seq_no/"$1-service-impl"

web_src_path="$web_path"/"src"

web_target_path="$web_path"/"target"

current_path="$app_release_path"/"$date_time""_""$seq_no"/"bin"

#######增加脚本版本文件#######

webPort_xml_file="$app_source_path/$1-web/pom.xml"

pom_xml_file="$app_source_path/pom.xml"

#######项目级别的config#######

pro_config_path="$app_release_path"/"config"

#######加入软连接#######

echo "web_path:"$web_path
echo "job_path:"$job_path
echo "service_impl_path:"$service_impl_path

#######生成可执行脚本#######
if [ -d "$app_source_path/$1-service-impl" ];then
sed '1,$s/$prefix/'"$1"'/g'  startService_tpl.sh > $shell_gen_base/startService.sh
chmod 755 "$shell_gen_base/startService".sh
fi
if [ -d "$app_source_path/$1-web" ];then

echo "输入的第二个参数:"$3

if [  -n "$3" ];then
echo "输入的端口号:"$3
sed -e '1,$s/$3/'"$3"'/g' -e '1,$s/$prefix/'"$1"'/g'  startWeb_tpl.sh > $shell_gen_base/startWeb.sh
chmod 755 "$shell_gen_base/startWeb".sh
else
str=`sed -n '/<jetty.port>/p' $webPort_xml_file`
#echo "我是user-----str="$str
delblankStr=$(echo $str)
port=${delblankStr:12:4}
echo "未输入端口号默认port:"$port
sed -e  '1,$s/$3/'"$port"'/g' -e '1,$s/$prefix/'"$1"'/g'  startWeb_tpl.sh > $shell_gen_base/startWeb.sh
chmod 755 "$shell_gen_base/startWeb".sh
fi
fi

sh switch.sh $release_base $1 $branch $current_version

#######动态生成job脚本#######
declare -a array
i=0
pro_str="$1"
pro_len=${#pro_str}
pro_len=$[ 11 + $pro_len ]
while read line
do
str=$(echo $line)
if [[ ${str:1:$pro_len} =  "module>"$1"-job" ]]; then
echo "读取pom文件:str="$str
#echo $str
str2=$(echo ${str#*>})
# echo "str2:"$str2
str3=$(echo ${str2%<*})
#echo "str3:"$str3
array[$i]=$str3
i=$[$i + 1]
fi
done < $pom_xml_file

for var in ${array[@]};do

echo "测试数据#######$var:"  $var
param1="$1"
strlen=${#param1}
start_shell=${var:$strlen:200}
echo "start_shell:"$start_shell
start_shell="start"$start_shell
echo "start_shell=========="$start_shell
rm -rf "$start_shell".sh
sed -e '1,$s/$3/'"$1"'/g' -e '1,$s/$prefix/'"$var"'/g' startJob_tpl.sh > "$shell_gen_base/$start_shell".sh
chmod 755 "$shell_gen_base/$start_shell".sh
#######创建路径#######
if [ ! -d "$job_path$var" ]; then
mkdir -p  "$job_path$var"
fi
#######打包job#######
done
for var in ${array[@]};do
cd $app_source_path/$var
mvn -gs $mvn_config -U clean assembly:assembly  ### >  /dev/null
done
####### if apps dir not exists then create #######
if [ ! -d "$release_base" ]; then
mkdir -p "$release_base"
fi

if [ ! -d "$global_pom_path" ]; then
mkdir -p "$global_pom_path"
fi

if [ ! -d "$current_path" ]; then
mkdir -p "$current_path"
fi

if [ ! -d "$log_path" ]; then
mkdir -p "$log_path"
fi

if [ ! -d "$global_config_path" ]; then
mkdir -p "$global_config_path"
fi

if [ -d "$app_source_path/$1-web" ];then
if [ ! -d "$web_target_path" ]; then
mkdir -p "$web_target_path"
fi
if [ ! -d "$web_src_path" ]; then
mkdir -p "$web_src_path"
fi
fi

if [ ! -d "$pro_config_path" ]; then
mkdir -p "$pro_config_path"
fi

#######打包 service #######

if [ -d "$app_source_path/$1-service-impl" ]; then
cd $app_source_path/$1-service-impl
mvn -gs $mvn_config clean assembly:assembly
fi
#######新增配置文件config#######
if [ -d "$app_source_path/config" ]; then
cp -Rpf $app_source_path/config  $pro_config_path
fi

#######构建程序发布目录#######

cd $app_release_path

rm -rf current

cp -Rpf $app_source_path/pom.xml  $global_pom_path

for var in ${array[@]};do

param1="$1"

strlen=${#param1}

start_shell=${var:$strlen:200}

start_shell="start"$start_shell

cp -Rpf $app_source_path/$var/target/*.tar.gz  $job_path$var
cp -Rpf $shell_gen_base/$start_shell.sh  $current_path

done

if [ -d "$app_source_path/$1-service-impl" ]; then
mkdir -p "$service_impl_path"
cp -Rpf $app_source_path/$1-service-impl/target/*.tar.gz  $service_impl_path
cp -Rpf $shell_gen_base/startService.sh  $current_path
fi

if [ -d "$app_source_path/$1-web" ]; then
if [ ! -d "$web_target_path" ]; then
mkdir -p "$web_target_path"
fi
if [ ! -d "$web_src_path" ]; then
mkdir -p "$web_src_path"
fi
cp -Rpf $app_source_path/$1-web/pom.xml  $web_path
cp -Rpf $app_source_path/$1-web/target/*.war $app_source_path/$1-web/target/$1-web $web_target_path
cp -Rpf $app_source_path/$1-web/src/* $app_source_path/$1-web/target/$1-web $web_src_path
cp -Rpf $shell_gen_base/startWeb.sh  $current_path
fi

echo "publish project execute over!!!"

sh tag.sh $1 $2 "$date_time""_"$seq_no $source_base $app_source_path

echo "当前版本号请记录:"$1/$2/"$date_time""_"$seq_no