package com.zy.admin.apimonitor.http;


import com.zy.admin.apimonitor.model.HttpRequest;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:58
 **/
public class HttpActivity {
    private HttpStrategy httpStrategy;

    public HttpActivity(HttpStrategy httpStrategy) {
        this.httpStrategy = httpStrategy;
    }

    public void execute(HttpRequest httpRequest){
        httpStrategy.doSend(httpRequest);
    }

}
