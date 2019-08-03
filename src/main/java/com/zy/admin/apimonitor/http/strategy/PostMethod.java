package com.zy.admin.apimonitor.http.strategy;


import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.http.HttpStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:49
 **/
public class PostMethod implements HttpStrategy {

    @Override
    public ResponseEntity<String> doSend(HttpRequest httpRequest) {

        String url = httpRequest.getUrl();

        HttpHeaders headers = new HttpHeaders();
        Map<String, String> map= new HashMap<>();
        if (!CollectionUtils.isEmpty(httpRequest.getHeaders())){
            httpRequest.getHeaders().forEach(httpHeader -> {
                headers.set(httpHeader.getHeaderName(),httpHeader.getHeaderValue());
            });
        }
        if (!CollectionUtils.isEmpty(httpRequest.getParameters())){
            httpRequest.getParameters().forEach(httpParameter -> {
                map.put(httpParameter.getParameterName(),httpParameter.getParameterValue());
            });
        }

        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,request,String.class);

        return responseEntity;


    }






}
