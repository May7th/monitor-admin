package com.zy.admin.system.config.support.filter;

import org.springframework.context.annotation.Configuration;

import com.zy.admin.system.config.support.filter.RequestLoggingFilter;

@Configuration
public class FilterConfig {
	
	
	RequestLoggingFilter rquestLogginFilter() {
		RequestLoggingFilter f = new RequestLoggingFilter();
		f.setLogEnabled(true);
		f.setIncludeHeaders(true);
		f.setIncludeQueryString(true);
		f.setIncludePayload(true);
		f.setIncludeResponse(true);
		f.setMaxPayloadLength(200);
		return f;
	}

}
