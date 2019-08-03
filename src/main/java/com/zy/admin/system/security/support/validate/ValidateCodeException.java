/**
 * 
 */
package com.zy.admin.system.security.support.validate;

import org.springframework.security.core.AuthenticationException;

/**
 * @author lenovo
 *
 */
public class ValidateCodeException extends  AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1170189980006964105L;

	
	public ValidateCodeException(String msg) {
		super(msg);
	}
}
