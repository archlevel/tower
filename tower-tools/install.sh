#!/bin/bash

##安装lifecycle-mapping-1.0.0


tools=`pwd`

echo "$tools"

##安装lifecycle-mapping
mvn install:install-file -Dfile=$tools/jars/lifecycle-mapping-1.0.0.jar -DpomFile=$tools/jars/lifecycle-mapping-1.0.0.pom


##安装memcache jar
mvn install:install-file -Dfile=$tools/jars/memcached-2.6.6.jar -DpomFile=$tools/jars/memcached-2.6.6.pom

