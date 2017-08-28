#!/bin/bash

cd ../../projects/$1

app_home_dir="$(pwd)"

if [ ! -d "$app_home_dir/$1-cache" ]; then
  sed -e /"<module>$1-cache<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-common" ]; then
  sed -e /"<module>$1-common<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-config" ]; then
  sed -e /"<module>$1-config<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-dao" ]; then
  sed -e /"<module>$1-dao<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
fi
if [ ! -d "$app_home_dir/$1-domain" ]; then
  sed -e /"<module>$1-cache<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-job" ]; then
  sed -e /"<module>$1-job<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-mq" ]; then
  sed -e /"<module>$1-mq<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-rpc" ]; then
  sed -e /"<module>$1-rpc<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-service" ]; then
  sed -e /"<module>$1-cache<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-service-impl" ]; then
  sed -e /"<module>$1-service-impl<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
if [ ! -d "$app_home_dir/$1-web" ]; then
  sed -e /"<module>$1-web<\/module>"/d pom.xml > tmp.xml
  cat tmp.xml > pom.xml
  rm -rf tmp.xml
fi
