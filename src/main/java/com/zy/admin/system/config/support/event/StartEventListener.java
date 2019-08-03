package com.zy.admin.system.config.support.event;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


/**
 * 启动事件
 * @author zy
 *
 */
@Slf4j
@Component
@EnableAsync
public class StartEventListener {

	@Async
	@Order(Ordered.LOWEST_PRECEDENCE)
	@EventListener(WebServerInitializedEvent.class)
	public void afterStart(WebServerInitializedEvent event) {
		String[] profiles = event.getApplicationContext().getEnvironment().getActiveProfiles();
		String profile = StringUtils.join(profiles,",");
		log.info("----项目启动完成，当前使用的环境变量:[{}]----", profile);
	}
	
}
