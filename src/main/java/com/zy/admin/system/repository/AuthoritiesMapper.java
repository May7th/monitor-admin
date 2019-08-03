package com.zy.admin.system.repository;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


import com.zy.admin.system.model.Authorities;

public interface AuthoritiesMapper extends BaseMapper<Authorities> {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> listByRoleIds(@Param("roleIds") List<Integer> roleIds);

    List<Authorities> listByRoleId(Integer roleId);
}
