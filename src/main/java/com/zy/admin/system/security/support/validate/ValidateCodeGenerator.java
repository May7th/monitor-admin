package com.zy.admin.system.security.support.validate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {
	
	ValidateCode generator(ServletWebRequest request);

}
