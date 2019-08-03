package com.zy.admin.apimonitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.admin.apimonitor.model.HttpHeader;

import com.zy.admin.apimonitor.repository.HttpHeaderMapper;
import com.zy.admin.apimonitor.service.IHttpHeaderService;
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
public class HttpHeaderServiceImpl extends ServiceImpl<HttpHeaderMapper, HttpHeader> implements IHttpHeaderService {

    @Autowired
    private HttpHeaderMapper httpHeaderMapper;

    @Override
    public List<HttpHeader> getListByRequestId(Long requestId){

        LambdaQueryWrapper<HttpHeader> queryWrapper = Wrappers.<HttpHeader>lambdaQuery();

        queryWrapper.eq(HttpHeader::getRequestId,requestId);

        return httpHeaderMapper.selectList(queryWrapper);

    }
}
