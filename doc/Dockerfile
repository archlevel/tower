#基本镜像
FROM centos
#把你上传的jdk放到docker容器里面的root目录下
ADD jdk-8u211-linux-x64.tar /root
#把你上传的tomcat放到docker容器里面的root目录下
ADD apache-tomcat-7.0.94.tar /root
#设置环境变量
ENV JAVA_HOME /root/jdk1.8.0_11
#设置环境变量
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
#设置环境变量
ENV CATALINA_BASE /root/apache-tomcat-7.0.57
#设置环境变量
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
#定义外部参数
ENV PARAMS=""
#执行startup.sh并打开日志
ENTRYPOINT echo $PARAMS > /root/params.txt && /root/apache-tomcat-7.0.57/getParams.sh && /root/apache-tomcat-7.-.57/bin/startup.sh && tail -F /root/apache-tomcat-7.0.57/logs/catalina.out

#启动命令
#docker run -d -etom PARAMS="user=root&pwd=lw123" -p 2000:8080 params