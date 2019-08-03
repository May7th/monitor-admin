package com.zy.admin.system.security.service;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.zy.admin.system.model.Authorities;
import com.zy.admin.system.model.User;
import com.zy.admin.system.repository.UserMapper;
import com.zy.admin.system.service.AuthoritiesService;
import com.zy.admin.system.utils.StringUtil;
/**
 * 
 * @author zy 
 * @date 2018-08-18 16:59
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthoritiesService authoritiesService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("账号不存在");
        }
        List<Authorities> authoritys =  authoritiesService.listByUserId(user.getUserId());
    	Set<String> dbAuthsSet = new HashSet<>();
    	authoritys.stream().filter(x -> StringUtil.isNotBlank(x.getAuthority())).forEach(x -> dbAuthsSet.add(x.getAuthority()));
    	Collection<? extends GrantedAuthority> authorities
		= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
    	user.setAuthorities(authorities);
        return user;
    }


}
