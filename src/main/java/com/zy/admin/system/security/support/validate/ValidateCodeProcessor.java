package com.zy.admin.system.security.support.validate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
	
	
	 
	 
	 /**
	  * 创建效验码
	  * @param request
	  */
	 void create(ServletWebRequest request) throws Exception;
	 
	 
	 /**
		 * 校验验证码
		 * 
		 * @param servletWebRequest
		 * @throws Exception
		 */
		void validate(ServletWebRequest servletWebRequest);
}
