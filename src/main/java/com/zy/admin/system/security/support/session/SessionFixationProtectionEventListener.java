package com.zy.admin.system.security.support.session;

import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SessionFixationProtectionEventListener implements ApplicationListener<SessionFixationProtectionEvent> {

	@Override
	 public void onApplicationEvent(SessionFixationProtectionEvent event) {  
		log.debug("sessionId 修改 "+event.getOldSessionId()+"|"+event.getNewSessionId());   
        String oldSessionId = event.getOldSessionId();  
        String newSessionId = event.getNewSessionId();  
                // 更改sessionId的值  
    }  

}
