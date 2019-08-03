package com.zy.admin.apimonitor;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-03 16:02
 **/
public class HttpTest {

    public static void main(String[] args) {

//        RestTemplate restTemplate = new RestTemplate();
//        String uri="http://localhost:8080/enableMonitor";
//        HttpHeaders headers = new HttpHeaders();
////        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("guid","874fbdc52d6b4e669f8ae2de5c0db5e1");
//        map.put("enabled","true");
//
////        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(null,headers);
//        String strbody=restTemplate.getForObject(uri,String.class,map);
//        System.out.println(strbody);

        String url = "http://oy.nmgoyun.com/api/fy/v1";

        HttpHeaders headers = new HttpHeaders();
       Map<String,String> map = new HashMap<>();

       map.put("inputStr","中国");
       map.put("pid","oyun-jk");
       map.put("type","5");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url,request,String.class);


        System.out.println(responseEntity.getBody());
    }
}
