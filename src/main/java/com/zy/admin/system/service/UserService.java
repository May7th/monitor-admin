package com.zy.admin.system.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.zy.admin.system.exception.BusinessException;
import com.zy.admin.system.exception.ParameterException;
import com.zy.admin.system.model.Role;
import com.zy.admin.system.model.User;
import com.zy.admin.system.model.UserRole;
import com.zy.admin.system.repository.UserMapper;
import com.zy.admin.system.repository.UserRoleMapper;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.PageResult;

@Service
public class UserService {

	private final UserMapper userMapper;
	
	private final UserRoleMapper userRoleMapper;


	
	public UserService(UserMapper userMapper, UserRoleMapper userRoleMapper) {
		this.userMapper = userMapper;
		this.userRoleMapper = userRoleMapper;
	}



	public PageResult<User> list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        QueryWrapper<User> wrapper =new QueryWrapper<>();
		if(StringUtil.isNotBlank(column)) {
			wrapper.like(column, value);
		}
		if(!showDelete) {
			  wrapper.eq("state", 0);
		}
		Page<User> userPage = new Page<>(pageNum, pageSize);
		List<User> userList = userMapper.selectPage(userPage, wrapper.orderBy(true,true,"create_time")).getRecords();
	    if (userList != null && userList.size() > 0) {
	    	 List<UserRole> userRoles = userRoleMapper.selectByUserIds(getUserIds(userList));
	    	   for (User one : userList) {
	    		   List<Role> tempURs = new ArrayList<>();
	    		   for (UserRole ur : userRoles) {
	                    if (one.getUserId().equals(ur.getUserId())) {
	                        tempURs.add(new Role(ur.getRoleId(), ur.getRoleName()));
	                    }
	                }
	                one.setRoles(tempURs);
	    	   }
	    }
		
	    return new PageResult<>(userPage.getTotal(), userList);
	}
	

    
    @Transactional(rollbackFor = Exception.class)
    public boolean add(User user) throws BusinessException {
        if (userMapper.getByUsername(user.getUsername()) != null) {
            throw new BusinessException("账号已经存在");
        }
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(finalSecret);
        user.setState(0);
        user.setCreateTime(new Date());
        boolean rs = userMapper.insert(user) > 0;
        if (rs) {
            List<Integer> roleIds = getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("添加失败，请重试");
            }
        }
        return rs;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public boolean update(User user) {
        user.setUsername(null);
        boolean rs = userMapper.updateById(user) > 0;
        if (rs) {
            Wrapper wrapper = new QueryWrapper<>().eq("user_id", user.getUserId());
            userRoleMapper.delete(wrapper);
            List<Integer> roleIds = getRoleIds(user.getRoles());
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("修改失败，请重试");
            }
        }
        return rs;
    }

    
    
    public boolean updateState(Integer userId, int state) throws ParameterException {
        if (state != 0 && state != 1) {
            throw new ParameterException("state值需要在[0,1]中");
        }
        User user = new User();
        user.setUserId(userId);
        user.setState(state);
        return userMapper.updateById(user) > 0;
    }
    
    
    
    
  
    public boolean updatePsw(Integer userId,String password) {
        User user = new User();
        user.setUserId(userId);
        String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(password);
        user.setPassword(finalSecret);
        return userMapper.updateById(user) > 0;
    }

    public User getById(Integer userId) {
        return userMapper.selectById(userId);
    }
    
    private List<Integer> getUserIds(List<User> userList) {
        List<Integer> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        return userIds;
    }
    
    /**
     * 添加用户角色
     */
    private List<Integer> getRoleIds(List<Role> roles) {
        List<Integer> rs = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                rs.add(role.getRoleId());
            }
        }
        return rs;
    }
    
    public List<User> selectByRoleId(Integer roleId){
    	return userMapper.selectByRoleId(roleId);
    }
    
	
}
