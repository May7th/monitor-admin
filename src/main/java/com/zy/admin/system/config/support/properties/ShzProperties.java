package com.zy.admin.system.config.support.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;



@ConfigurationProperties("shz")
public class ShzProperties {
	
	@Getter@Setter
	SecurityProperties security =new SecurityProperties();


}
