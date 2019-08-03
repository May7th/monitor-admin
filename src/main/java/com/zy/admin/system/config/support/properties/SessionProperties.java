/**
 * 
 */
package com.zy.admin.system.config.support.properties;

import lombok.Data;

@Data
public class SessionProperties {
	
	private int maximumSessions = 1;

	private boolean maxSessionsPreventsLogin = false;


	
}
