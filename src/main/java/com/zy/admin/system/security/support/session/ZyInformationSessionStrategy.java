package com.zy.admin.system.security.support.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.admin.system.utils.results.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ZyInformationSessionStrategy implements SessionInformationExpiredStrategy {

	private ObjectMapper objectMapper = new ObjectMapper();
	
	/*private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();*/
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.debug("session已经失效可能是并发登录");
		 JsonResult result =new JsonResult();
		 result.setCode(401);
		 result.setMessage("session已经失效由于并发登录"+"|请联系管理员");
		 event.getResponse().setStatus(HttpStatus.OK.value());
		 event.getResponse().setContentType("application/json;charset=UTF-8");
		 event.getResponse().getWriter().write(objectMapper.writeValueAsString(result));
		 event.getRequest().getSession();
		/* redirectStrategy.sendRedirect(event.getRequest(), event.getResponse(), "/logout");*/
		
	}

}
