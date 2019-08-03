package com.zy.admin.apimonitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.admin.apimonitor.model.HttpHeader;
import com.zy.admin.apimonitor.model.HttpParameter;
import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.http.HttpStrategy;
import com.zy.admin.apimonitor.http.HttpStrategyFactory;

import com.zy.admin.apimonitor.repository.HttpRequestMapper;
import com.zy.admin.apimonitor.service.IHttpHeaderService;
import com.zy.admin.apimonitor.service.IHttpParameterService;
import com.zy.admin.apimonitor.service.IHttpRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * http请求表 服务实现类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@Service
public class HttpRequestServiceImpl extends ServiceImpl<HttpRequestMapper, HttpRequest> implements IHttpRequestService {

    @Autowired
    private HttpRequestMapper httpRequestMapper;

    @Autowired
    private IHttpHeaderService httpHeaderService;

    @Autowired
    private IHttpParameterService httpParameterService;


    public boolean httpRequestHandler(Long groupId){

        HttpRequest httpRequest = getByGroupId(groupId);

        String httpMethod = httpRequest.getHttpMethod();

        HttpStrategy strategy = HttpStrategyFactory.getStrategy(httpMethod.toLowerCase().trim());

        ResponseEntity<String> responseEntity = strategy.doSend(httpRequest);

        return false;

    }

    @Override
    public HttpRequest getByGroupId(Long groupId){

        LambdaQueryWrapper<HttpRequest> lambdaQueryWrapper = Wrappers.<HttpRequest>lambdaQuery();
        lambdaQueryWrapper.eq(HttpRequest::getGroupId,groupId);



        HttpRequest httpRequest = httpRequestMapper.selectOne(lambdaQueryWrapper);

        List<HttpHeader> headers = httpHeaderService.getListByRequestId(httpRequest.getId());

        List<HttpParameter> parameters = httpParameterService.getListByRequestId(httpRequest.getId());

        httpRequest.setParameters(parameters);
        httpRequest.setHeaders(headers);

        return httpRequest;
    }


}
