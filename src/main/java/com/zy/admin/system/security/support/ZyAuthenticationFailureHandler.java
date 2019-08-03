package com.zy.admin.system.security.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.admin.system.security.support.validate.ValidateCodeException;
import com.zy.admin.system.utils.results.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ZyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		//LockedException 用户被绑定
		//BadCredentialsException 坏的凭证
		 log.debug("登录失败:"+exception.getMessage()+"|"+exception.getClass());
		 JsonResult result =new JsonResult();
		 result.setCode(400);
		 if (exception instanceof BadCredentialsException) {
			 result.setMessage("账号或密码错误");
		 }else if(exception instanceof LockedException) {
			 result.setMessage("账号被锁定");
		 }else if(exception instanceof ValidateCodeException) {
			 result.setMessage(exception.getMessage());
		 }
		response.setStatus(HttpStatus.OK.value());
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}


	
	
	
}
