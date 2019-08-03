package com.zy.admin.system.security.support.session;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** 
 * 监听session失效事件 
 */  
@Component  
@Slf4j  
public class SessionDestroyedEventListener implements ApplicationListener<HttpSessionDestroyedEvent> {
	
	 @Override  
	    public void onApplicationEvent(HttpSessionDestroyedEvent event) {  
	        log.debug("失效session:{}", event.getSession().getId());  
	        try {  
	            // 移除session  
	        } catch (Exception e) {  
	            log.error(String.format("失效session:[%s]发生异常.", event.getId()), e);  
	        }  
	    }  
}
