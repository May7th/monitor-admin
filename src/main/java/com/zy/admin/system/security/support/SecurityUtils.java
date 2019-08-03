package com.zy.admin.system.security.support;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.zy.admin.system.model.User;

/**
 * 安全工具类
 *
 * @author zy
 */
public class SecurityUtils {

	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 */
	public static User getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof User) {
			return ((User) principal);
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static User getUser() {
		Authentication authentication = getAuthentication();
		if (authentication == null) {
			return null;
		}
		return getUser(authentication);
	}

}
