/**
 * 
 */
package com.zy.admin.system.config.support.properties;

import lombok.Data;

@Data
public class ImageCodeProperties {

	private int width = 150;
	
	private int height = 40;
	
	private int length = 4;
	
	private int expireIn = 1;
	
	private Boolean open = true;
	

}
