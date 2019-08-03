package com.zy.admin.system.repository;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import com.zy.admin.system.model.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> selectByUserIds(@Param("userIds") List<Integer> userIds);

    int insertBatch(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);
}
