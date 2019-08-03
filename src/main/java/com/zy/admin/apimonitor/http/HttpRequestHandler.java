package com.zy.admin.apimonitor.http;


import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.model.RequestLog;
import com.zy.admin.apimonitor.service.IHttpRequestService;
import com.zy.admin.apimonitor.service.IRequestLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:36
 **/
@Service
public class HttpRequestHandler {

    @Autowired
    private IHttpRequestService httpRequestService;

    @Autowired
    private IRequestLogService requestLogService;

    public void doHandler(Long groupId){

        HttpRequest httpRequest = httpRequestService.getByGroupId(groupId);

        RequestLog requestLog = new RequestLog();
        requestLog.setGroupId(groupId);
        requestLog.setRequestId(httpRequest.getId());

        long startTime = System.currentTimeMillis();
        ResponseEntity responseEntity = sendRequest(httpRequest);
        long endTime = System.currentTimeMillis();
        long costTime = endTime-startTime;


        int statusCode = responseEntity.getStatusCodeValue();
        String responseBody = null;
        try {
            responseBody = new String(responseEntity.getBody().toString().getBytes("ISO8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestLog.setStatusCode(String.valueOf(statusCode));
        requestLog.setStatus(true);
        try {
            validResponse(requestLog,httpRequest,responseBody,String.valueOf(statusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (HttpStatus.OK.value()!= statusCode){
            requestLog.setLog(responseEntity.getBody().toString());
        }else {
            requestLog.setResponseBody(responseBody);
        }
        requestLog.setCostTime(Integer.parseInt(Long.toString(costTime)));

        requestLogService.save(requestLog);

    }

    private ResponseEntity sendRequest(HttpRequest httpRequest){

        String httpMethod = httpRequest.getHttpMethod();

        HttpStrategy strategy = HttpStrategyFactory.getStrategy(httpMethod.toLowerCase().trim());


        ResponseEntity<String> responseEntity;
        try {

            responseEntity = strategy.doSend(httpRequest);

        }catch (Exception e){
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return responseEntity;

    }

    protected void validResponse(RequestLog requestLog,HttpRequest httpRequest,String body, String statusCode) throws Exception {
        switch (httpRequest.getConditionType()) {
            case CONTAINS:
                if (StringUtils.isEmpty(body) || !body.contains(httpRequest.getConditions())) {
                requestLog.setStatus(false);
                }
                break;
            case DOESNT_CONTAIN:
                if (StringUtils.isEmpty(body) || body.contains(httpRequest.getConditions())) {
                    requestLog.setStatus(false);
                }
                break;
            case STATUSCODE:
                if (!statusCode.equals(httpRequest.getConditions())) {
                    requestLog.setStatus(false);
                }
                break;
            default:
                if (!"200".equals(statusCode)) {
                    requestLog.setStatus(false);
                }
                break;
        }

    }



}
