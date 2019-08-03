package com.zy.admin.apimonitor.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.admin.apimonitor.model.HttpParameter;

import com.zy.admin.apimonitor.repository.HttpParameterMapper;
import com.zy.admin.apimonitor.service.IHttpParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-24
 */
@Service
public class HttpParameterServiceImpl extends ServiceImpl<HttpParameterMapper, HttpParameter> implements IHttpParameterService {

    @Autowired
    private HttpParameterMapper httpParameterMapper;

    @Override
    public List<HttpParameter> getListByRequestId(Long requestId) {

        LambdaQueryWrapper<HttpParameter> queryWrapper = Wrappers.<HttpParameter>lambdaQuery();

        queryWrapper.eq(HttpParameter::getRequestId,requestId);

        return httpParameterMapper.selectList(queryWrapper);
    }
}
