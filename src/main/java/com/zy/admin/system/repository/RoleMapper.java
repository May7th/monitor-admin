package com.zy.admin.system.repository;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.zy.admin.system.model.Role;

public interface RoleMapper extends BaseMapper<Role> {

    List<Role> selectByUserId(Integer userId);
}
