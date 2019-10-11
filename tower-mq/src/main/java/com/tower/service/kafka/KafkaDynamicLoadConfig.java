package com.tower.service.kafka;

import com.mgzf.sdk.mq.kafka.config.ApolloUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * kafka动态加载配置类
 *
 * @author moxianglin
 * public  Logger initLog(){
 * //		// 生成新的Appender
 * //		FileAppender appender = new RollingFileAppender();
 * //		PatternLayout layout = new PatternLayout();
 * //		String conversionPattern = "%m%n";
 * //		layout.setConversionPattern(conversionPattern);
 * //		appender.setLayout(layout);
 * //		// log输出路径
 * //		appender.setFile( "logs/producer.log");
 * //		// true:在已存在log文件后面追加 false:新log覆盖以前的log
 * //		appender.setAppend(true);
 * //		// 适用当前配置
 * //		appender.activateOptions();
 * //		// 将新的Appender加到Logger中
 * //		logger.addAppender(appender);
 * //		return logger;
 * //	}
 */

@Component
public class KafkaDynamicLoadConfig implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		ApolloUtils.readConf(null);
	}


}
