package com.zy.admin.system.security.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.admin.system.config.support.event.LoginRecordEvent;
import com.zy.admin.system.model.LoginRecord;
import com.zy.admin.system.utils.SpringUtils;
import com.zy.admin.system.utils.UserAgentGetter;
import com.zy.admin.system.utils.results.JsonResult;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
/**
 * 
 * @author zy 
 * @date 2018-08-18 16:57
 */
public class ZyAuthenticationSuccessHandler  extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private ObjectMapper objectMapper;
	

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
	     log.debug("登入成功");
	     addLoginRecord(SecurityUtils.getUser().getUserId(),request);
		 JsonResult result =new JsonResult();
		 result.setCode(200);
		 result.setMessage("登录成功");
		 response.setStatus(HttpStatus.OK.value());
		 response.setContentType("application/json;charset=UTF-8");
		 response.getWriter().write(objectMapper.writeValueAsString(result)); 
	}
	
    private void addLoginRecord(Integer userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        // 添加到登录日志
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
      // 发送 spring event 事件
        SpringUtils.publishEvent(new LoginRecordEvent(loginRecord));
    }

}
