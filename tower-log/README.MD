### log包

### 遵从log4j，目前实现是logback，对logback，对slf4j进行了封装
### 所有日志必需使用com.tower.service.log.LoggerFactory;com.tower.service.log.Logger;
### 所有log配置都是采用logback
### 所有模块的日志文件必需包含错误与信息两个文件