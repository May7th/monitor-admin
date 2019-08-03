package com.zy.admin.system.security.support;


import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.zy.admin.system.model.User;
import com.zy.admin.system.utils.StringUtil;

/**
 * 
 * @author zy 
 * @date 2018-08-18 16:57
 * 权限判断
 *
 * url: https://stackoverflow.com/questions/41434231/use-spring-security-in-thymeleaf-escaped-expressions-in-javascript
 */
@Service("sec")
public class SecService {

	private PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 提供给页面输出当前用户
	 * @return {AuthUser}
	 */
	public User currentUser() {
		return SecurityUtils.getUser();
	}

	/**
	 * 判断请求是否有权限
	 *
	 * @param request        HttpServletRequest
	 * @param authentication 认证信息
	 * @return 是否有权限
	 */
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		User user = SecurityUtils.getUser(authentication);
		if (user == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		if (authorities.isEmpty()) {
			return false;
		}
		//Todo 
		return true;
	}

	/**
	 * 判断按钮是否有xxx:xxx权限
	 * @param permission 权限
	 * @return {boolean}
	 */
	public boolean hasPermission(String permission) {
		if (StringUtil.isBlank(permission)) {
			return false;
		}
		Authentication authentication = SecurityUtils.getAuthentication();
		if (authentication == null) {
			return false;
		}
		User user = SecurityUtils.getUser(authentication);
		if (user == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			if (StringUtil.isBlank(authority)) {
				continue;
			}
			if (authority.equalsIgnoreCase(permission)) {
				return true;
			}
		}
		return false;
	}
}
