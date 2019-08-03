package com.zy.admin.system.security.support.validate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;



@Component("validateCodeFilter")
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements
		InitializingBean {

	/**
	 * 验证码校验失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler zyAuthenticationFailureHandler;

	/**
	 * 验证码校验成功处理器
	 */
	@Autowired
	private AuthenticationSuccessHandler zyAuthenticationSuccessHandler;

	/**
	 * 系统中的校验码处理器
	 */
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;

	/**
	 * 存放所有需要校验验证码的url
	 */
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	/**
	 * 验证请求url与配置的url是否匹配的工具类
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
		urlMap.put("/authentication/form", ValidateCodeType.IMAGE);
		//addUrlToMap("/user/*",ValidateCodeType.IMAGE);

		//urlMap.put("/authentication/mobile", ValidateCodeType.SMS);
		//addUrlToMap("/user/*",ValidateCodeType.SMS);
	}

	/**
	 * 讲系统中配置的需要校验验证码的URL根据校验的类型放入map
	 * 
	 * @param urlString
	 * @param type
	 */
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(
					urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			log.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型"
					+ type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				log.info("验证码校验通过");
			} catch (ValidateCodeException e) {
				zyAuthenticationFailureHandler.onAuthenticationFailure(request,
						response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}

}
