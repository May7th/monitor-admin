package com.zy.admin.system.security.support.validate;

import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.ServletWebRequest;

@Repository
public interface ValidateCodeRepository {
	
	 void save(ServletWebRequest request,ValidateCode validateCode,ValidateCodeType type);
	 
	 ValidateCode get(ServletWebRequest request,ValidateCodeType type);
	 
	 void remove(ServletWebRequest request,ValidateCodeType type); 
	

}
