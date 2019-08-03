package com.zy.admin.system.security.support.session;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/** 
 * 监听session创建对象 
 */  
@Component  
@Slf4j  
public class HttpSessionCreatedEventListener implements ApplicationListener<HttpSessionCreatedEvent> {@Override
	
	public void onApplicationEvent(HttpSessionCreatedEvent event) {
	  log.debug("新建session:{}", event.getSession().getId());  
      try {  
          // 保存 session  
      } catch (Exception e) {  
          log.error(String.format("添加session:[%s]出现异常.", event.getSession().getId()), e);  
      }  
		
	}

}
