package com.zy.admin.apimonitor.http;


import com.zy.admin.apimonitor.model.HttpRequest;
import org.springframework.http.ResponseEntity;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:46
 **/
public interface HttpStrategy {

    ResponseEntity<String> doSend(HttpRequest httpRequest);
}
