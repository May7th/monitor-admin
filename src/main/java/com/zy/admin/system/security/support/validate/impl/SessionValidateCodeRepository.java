package com.zy.admin.system.security.support.validate.impl;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.zy.admin.system.security.support.validate.ValidateCode;
import com.zy.admin.system.security.support.validate.ValidateCodeRepository;
import com.zy.admin.system.security.support.validate.ValidateCodeType;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class SessionValidateCodeRepository implements ValidateCodeRepository {
	
	
	/**
	 * key
	 */
	 String SESSION_KEY="SESSION_KEY_FOR_CODE_";

	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	
	@Override
	public void save(ServletWebRequest request, ValidateCode validateCode,ValidateCodeType type) {
		// TODO Auto-generated method stub
		log.debug("构建验证码放入session时的key:"+type.toString().toUpperCase());
		sessionStrategy.setAttribute(request,getSessionKey(type),validateCode);

	}

	@Override
	public ValidateCode get(ServletWebRequest request,ValidateCodeType type) {
		// TODO Auto-generated method stub
		ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(type));
		return codeInSession;
	}

	@Override
	public void remove(ServletWebRequest request,ValidateCodeType type) {
		// TODO Auto-generated method stub
		sessionStrategy.removeAttribute(request, getSessionKey(type));
	}
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ValidateCodeType type) {
		return SESSION_KEY + type.toString().toUpperCase();
	}

	

}
