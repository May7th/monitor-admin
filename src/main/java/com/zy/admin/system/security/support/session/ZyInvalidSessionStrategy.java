package com.zy.admin.system.security.support.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zy.admin.system.utils.results.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ZyInvalidSessionStrategy implements InvalidSessionStrategy {

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("session已经失效可能是超时了");
		 JsonResult result =new JsonResult();
		 result.setCode(401);
		 result.setMessage("session已经失效由于超时了");
		 response.setStatus(HttpStatus.OK.value());
		 response.setContentType("application/json;charset=UTF-8");
		 response.getWriter().write(objectMapper.writeValueAsString(result));
		 request.getSession();
		
	}


}
