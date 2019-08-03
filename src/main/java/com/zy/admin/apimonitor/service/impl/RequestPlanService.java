package com.zy.admin.apimonitor.service.impl;

import com.zy.admin.apimonitor.model.GroupPlan;
import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.model.RequestPlan;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: zy-admin
 * @description:
 * @author: changzhen
 * @create: 2019-08-03 13:23
 **/
@Service
public class RequestPlanService {

    public GroupPlan assembleGroupPlan(RequestPlan requestPlan){

        GroupPlan groupPlan = new GroupPlan();
        Long groupId = Long.valueOf(requestPlan.getGroupId());
        String systemName = requestPlan.getSystemName();
        String frequency = requestPlan.getFrequency();
        String monitorName = requestPlan.getName();

        groupPlan.setId(groupId);
        groupPlan.setFrequency(frequency);
        groupPlan.setEnabled(false);
        groupPlan.setSystemName(systemName);
        groupPlan.setName(monitorName);

        return groupPlan;
    }

    public HttpRequest assembleRequest(RequestPlan requestPlan){
        HttpRequest httpRequest = new HttpRequest();
        Long id = Long.valueOf(requestPlan.getRequestId());
        httpRequest.setId(id);
        String httpMethod = requestPlan.getHttpMethod();
        String url = requestPlan.getUrl();
        String conditionType = requestPlan.getConditionType();
        String conditions = null;
        if (!HttpRequest.CheckCondition.DEFAULT.equals(conditionType)){
            conditions = requestPlan.getConditions();
            httpRequest.setConditions(conditions);
        }
        Long groupId = Long.valueOf(requestPlan.getGroupId());
        httpRequest.setGroupId(groupId);
        httpRequest.setHttpMethod(httpMethod);
        httpRequest.setUrl(url);
        httpRequest.setConditionType(HttpRequest.CheckCondition.valueOf(conditionType));
        httpRequest.setCreateTime(new Date());

        return httpRequest;
    }


}
