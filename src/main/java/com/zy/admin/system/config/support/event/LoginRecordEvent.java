package com.zy.admin.system.config.support.event;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * 登录日志事件
 * @author zy 
 * @date 2018-09-20 10:45
 */

public class LoginRecordEvent extends ApplicationEvent {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3385094095220989234L;

	public LoginRecordEvent(Object source) {
		super(source);
	}

}
