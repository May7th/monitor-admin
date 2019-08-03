package com.zy.admin.system.repository;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


import com.zy.admin.system.model.RoleAuthorities;

public interface RoleAuthoritiesMapper extends BaseMapper<RoleAuthorities> {

    int insertRoleAuths(@Param("roleId") Integer roleId, @Param("authIds") List<Integer> authIds);

}
