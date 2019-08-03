package com.zy.admin.system.config.support.properties;

import lombok.Getter;
import lombok.Setter;

public class SecurityProperties {
	
	@Getter@Setter
	SessionProperties session = new SessionProperties();
	
	@Getter@Setter
	ImageCodeProperties imageCode = new ImageCodeProperties();

}
