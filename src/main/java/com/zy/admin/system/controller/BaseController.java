package com.zy.admin.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.zy.admin.system.model.User;
/**
 * 
 * @author zy 
 * @date 2018-08-18 16:58
 */
public class BaseController {
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	public User getLoginUser(){
		Authentication authentication =	SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			Object object =  authentication.getPrincipal();
			if(object != null) {
				return (User) object;
			}
		}
		return null;
	}
	
	   /**
     * 获取当前登录的userId
     */
    public Integer getLoginUserId() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUserId();
    }

    /**
     * 获取当前登录的username
     */
    public String getLoginUserName() {
        User loginUser = getLoginUser();
        return loginUser == null ? null : loginUser.getUsername();
    }
    
    protected String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}
    
    /**
     * 如果修改了角色的权限 或者 修改了用户的角色 让那用户session失效
     * @param user
     */
    protected void invalidateSession(User user){
    	  List<Object> o= sessionRegistry.getAllPrincipals();
          for (Object principal : o) {
              if (principal instanceof User) {
                  final User loggedUser = (User) principal;
                  if (user.getUsername().equals(loggedUser.getUsername())) {
                      List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
                      if (null != sessionsInfo && sessionsInfo.size() > 0) {
                          for (SessionInformation sessionInformation : sessionsInfo) {
                              sessionInformation.expireNow();
                          }
                      }
                  }
              }
          }
    }

}

    