package com.zy.admin.apimonitor.http;


import com.zy.admin.apimonitor.http.strategy.GetMethod;
import com.zy.admin.apimonitor.http.strategy.PostMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-17 15:50
 **/
public class HttpStrategyFactory {

    private static Map<String,HttpStrategy> HTTP_STRATEGY_MAP = new HashMap<>();

    static {
        HTTP_STRATEGY_MAP.put("get",new GetMethod());
        HTTP_STRATEGY_MAP.put("post",new PostMethod());
    }

    private HttpStrategyFactory(){}

    public static HttpStrategy getStrategy(String methodKey){
        HttpStrategy requestStrategy = HTTP_STRATEGY_MAP.get(methodKey);
        return requestStrategy;
    }

}
