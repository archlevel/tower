## 工具用法

### 框架代码生成器
+ cd tower-tools
+ 运行 ./gen_xxxx.sh 脚本
	+ ./gen_all.sh 项目名 [公司名] 生成项目所有模块,公司名称默认为siling
	+ ./gen_job.sh 项目名 [公司名] 生成项目job所需的相关模块,公司名称默认为siling
	+ ./gen_service.sh 项目名 [公司名] 生成项目service所需的相关模块,公司名称默认为siling
	+ ./gen_web.sh 项目名 [公司名] 生成项目web所需的相关模块,公司名称默认为siling
	
### 部署	

+ 安装发布脚本
	+ mkdir -p /root/shell_bash
	+ scp -rp tower-tools/publish/*.sh root@发布机器/root/shell_bash/
	+ [设置keychain](publish/README.md)
	
+ 发布代码
	+ login 2 部署机器	
	+ cd /root/shell_bash
	+ publish.sh 项目名称 分支名
	+ rsyc.sh 项目名称 分支名 版本号 远程IP地址 用户名
	+ current/start_job.sh
	+ current/startService.sh
	+ current/startWeb.sh
	
