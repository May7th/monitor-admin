package com.zy.admin.system.security.support.validate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zy.admin.system.security.support.validate.image.ImageCodeGenerator;

@Configuration
public class ValidateCodeBeanConfig {
	
	
	
	@Bean
	@ConditionalOnMissingBean(name = "imageCodeGenerator")
	public ValidateCodeGenerator imageCodeGenerator(){
		ImageCodeGenerator CodeGenerator =new ImageCodeGenerator();
		return CodeGenerator;
	}
	
	
/*	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender(){
	return new DefaultSmsCodeSender();
	}
	*/
}
