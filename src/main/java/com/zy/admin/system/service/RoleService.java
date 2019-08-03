package com.zy.admin.system.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.zy.admin.system.exception.ParameterException;
import com.zy.admin.system.model.Role;
import com.zy.admin.system.repository.RoleAuthoritiesMapper;
import com.zy.admin.system.repository.RoleMapper;

@Service
public class RoleService {

	private final  RoleMapper roleMapper;
	private final RoleAuthoritiesMapper roleAuthoritiesMapper;

	public RoleService(RoleMapper roleMapper, RoleAuthoritiesMapper roleAuthoritiesMapper) {
		this.roleMapper = roleMapper;
		this.roleAuthoritiesMapper = roleAuthoritiesMapper;
	}

	public List<Role> list(boolean showDelete) {
        QueryWrapper wrapper = new QueryWrapper();
        if (!showDelete) {
            wrapper.eq("is_delete", 0);
        }
        return roleMapper.selectList(wrapper.orderBy(true,true,"create_time"));
    }

	 public boolean add(Role role) {
	        role.setCreateTime(new Date());
	        return roleMapper.insert(role) > 0;
	    }

	 public boolean update(Role role) {
	        return roleMapper.updateById(role) > 0;
	    }

	    @Transactional(rollbackFor = Exception.class)
	    public boolean updateState(Integer roleId, int isDelete) {
	        if (isDelete != 0 && isDelete != 1) {
	            throw new ParameterException("isDelete值需要在[0,1]中");
	        }
	        Role role = new Role();
	        role.setRoleId(roleId);
	        role.setIsDelete(isDelete);
	        boolean rs = roleMapper.updateById(role) > 0;
	        if (rs) {
	            //删除角色的权限
                QueryWrapper wrapper = new QueryWrapper();
                wrapper.eq("role_id", roleId);
	            roleAuthoritiesMapper.delete(wrapper);
	        }
	        return rs;
	    }
	 
	 public boolean delete(Integer roleId) {
	        return roleMapper.deleteById(roleId) > 0;
	    }

}
