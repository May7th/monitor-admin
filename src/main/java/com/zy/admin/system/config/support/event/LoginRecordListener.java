package com.zy.admin.system.config.support.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.zy.admin.system.model.LoginRecord;
import com.zy.admin.system.service.LoginRecordService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginRecordListener {
	
	@Autowired
	private LoginRecordService loginRecordService;
	

	@Async
	@Order
	@EventListener(LoginRecordEvent.class)
	public void saveLoginRecord(LoginRecordEvent event) {
		LoginRecord loginRecord = (LoginRecord) event.getSource();
		loginRecordService.add(loginRecord);
		log.debug("监听到登录日志事件 保存成功");
	}

}
