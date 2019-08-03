package com.zy.admin.system.security.support.validate;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;

import com.zy.admin.system.config.support.properties.ShzProperties;


@Component("validateCodeSecurityConfig")
public class ValidateCodeSecurityConfig  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {	
	
	@Autowired
	private Filter validateCodeFilter;
	
	@Autowired
	private ShzProperties shzProperties;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		if(shzProperties.getSecurity().getImageCode().getOpen()) {
		http.addFilterBefore(validateCodeFilter, AbstractPreAuthenticatedProcessingFilter.class);
		}
	}
	
}
