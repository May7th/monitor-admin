package com.zy.admin.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.zy.admin.system.config.support.properties.SecurityConstants;
import com.zy.admin.system.config.support.properties.ShzProperties;
import com.zy.admin.system.security.support.validate.ValidateCodeSecurityConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter{
	
	@Autowired
	AuthenticationFailureHandler zyAuthenticationFailureHandler;
	
	@Autowired
	AuthenticationSuccessHandler zyAuthenticationSuccessHandler;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	
	@Autowired
	private ShzProperties shzProperties;
	
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.apply(validateCodeSecurityConfig)
		.and()
		  .formLogin()
		  .loginPage(SecurityConstants.DEFAULT_LOGIN_PAGE)
		  .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
		  .successHandler(zyAuthenticationSuccessHandler)
		  .failureHandler(zyAuthenticationFailureHandler)
		.and()
		  .logout()
		  .logoutSuccessUrl(SecurityConstants.DEFAULT_LOGIN_PAGE)
		  .deleteCookies("JSESSIONID")
		.and()
		  .userDetailsService(userDetailsService)
		  .authorizeRequests()
		  .antMatchers(SecurityConstants.DEFAULT_LOGIN_PAGE).permitAll()
		  .antMatchers("/static/**").permitAll()
		  .antMatchers("/code/*").permitAll()
		  .antMatchers("/druid/**").permitAll()
		  .anyRequest().authenticated()
		.and()
		  .sessionManagement()
		  .invalidSessionStrategy(invalidSessionStrategy)
		  .maximumSessions(shzProperties.getSecurity().getSession().getMaximumSessions())
		  .sessionRegistry(getSessionRegistry())
		  .maxSessionsPreventsLogin(shzProperties.getSecurity().getSession().isMaxSessionsPreventsLogin())
		  .expiredSessionStrategy(sessionInformationExpiredStrategy)
		.and()
		.and()
		  .headers().frameOptions().disable()
		.and()
		   .csrf().disable();
		
	}
	
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SessionRegistry getSessionRegistry(){
        SessionRegistry sessionRegistry=new SessionRegistryImpl();
        return sessionRegistry;
    }

    
    @Bean  
    public ServletListenerRegistrationBean httpSessionEventPublisher() {  
        ServletListenerRegistrationBean<HttpSessionEventPublisher> registration = new ServletListenerRegistrationBean<>();  
        registration.setListener(new HttpSessionEventPublisher());  
        return registration;  
    }  

	
	
}
