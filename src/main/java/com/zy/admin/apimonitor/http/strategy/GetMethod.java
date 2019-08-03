package com.zy.admin.apimonitor.http.strategy;


import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.http.HttpStrategy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:49
 **/
public class GetMethod implements HttpStrategy {

    @Override
    public ResponseEntity<String> doSend(HttpRequest httpRequest) {

        AtomicReference<String> url = new AtomicReference<>(httpRequest.getUrl());

        HttpHeaders headers = new HttpHeaders();

        if (!CollectionUtils.isEmpty(httpRequest.getHeaders())){
            httpRequest.getHeaders().forEach(httpHeader -> {
                headers.set(httpHeader.getHeaderName(),httpHeader.getHeaderValue());
            });
        }

        if (!CollectionUtils.isEmpty(httpRequest.getParameters())){
            url.set(url + "?");
            httpRequest.getParameters().forEach(httpParameter -> {
                url.set(url + httpParameter.getParameterName()
                        + "=" + httpParameter.getParameterValue()
                        +"&");
            });
            url.set(url.get().substring(0,url.get().length()-1));
        }

        System.out.println(url.get());
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url.get(), String.class,headers);

        return responseEntity;
    }






}
