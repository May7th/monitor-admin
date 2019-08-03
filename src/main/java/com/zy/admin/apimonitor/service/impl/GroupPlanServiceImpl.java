package com.zy.admin.apimonitor.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.admin.apimonitor.model.GroupPlan;

import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.model.RequestPlan;
import com.zy.admin.apimonitor.repository.GroupPlanMapper;
import com.zy.admin.apimonitor.repository.HttpRequestMapper;
import com.zy.admin.apimonitor.service.IGroupPlanService;
import com.zy.admin.apimonitor.service.IHttpRequestService;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * http序列表 服务实现类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@Service
public class GroupPlanServiceImpl extends ServiceImpl<GroupPlanMapper, GroupPlan> implements IGroupPlanService {

    @Autowired
    private IHttpRequestService httpRequestService;

    @Autowired
    private HttpRequestMapper httpRequestMapper;

    @Autowired
    private GroupPlanMapper groupPlanMapper;

    @Autowired
    private RequestPlanService requestPlanService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRequestAndPlan(RequestPlan requestPlan) {

        Long groupId = groupPlanHandler(requestPlan);

        requestPlan.setGroupId(groupId.toString());
        if (groupId == null){
            return null;
        }
        return httpRequestHandler(requestPlan);

    }

    @Override
    public PageResult<RequestPlan> list(int pageNum, int pageSize, boolean showDelete, String column, String value) {
        QueryWrapper<HttpRequest> wrapper =new QueryWrapper<>();
        if(StringUtil.isNotBlank(column)) {
            wrapper.like(column, value);
        }
        if(!showDelete) {
            wrapper.eq("archived", 0);
        }
        Page<HttpRequest> httpRequestPage = new Page<>(pageNum, pageSize);
        List<HttpRequest> httpRequestList = httpRequestMapper.selectPage(httpRequestPage,
                wrapper.orderBy(true,true,"create_time")).getRecords();
        List<RequestPlan> requestPlanList = new ArrayList<>();
        if (httpRequestList != null && httpRequestList.size() > 0) {
            for (HttpRequest httpRequest:
                 httpRequestList) {
                GroupPlan groupPlan = groupPlanMapper.selectById(httpRequest.getGroupId());
                RequestPlan requestPlan = new RequestPlan();
                requestPlan.setRequestId(httpRequest.getId().toString());
                requestPlan.setGroupId(groupPlan.getId().toString());
                requestPlan.setName(groupPlan.getName());
                requestPlan.setSystemName(groupPlan.getSystemName());
                requestPlan.setConditions(httpRequest.getConditions());
                requestPlan.setConditionType(httpRequest.getConditionType().name());
                requestPlan.setEnable(groupPlan.getEnabled());
                requestPlan.setFrequency(groupPlan.getFrequency());
                requestPlan.setHttpMethod(httpRequest.getHttpMethod());
                requestPlan.setRemark(groupPlan.getRemark());
                requestPlan.setUrl(httpRequest.getUrl());
                requestPlan.setCreateTime(httpRequest.getCreateTime());
                requestPlanList.add(requestPlan);
            }
        }

        return new PageResult<>(httpRequestPage.getTotal(), requestPlanList);
    }

    @Override
    public Boolean updateRequestAndPlan(RequestPlan requestPlan) {
        String requestId = requestPlan.getRequestId();
        String groupId = requestPlan.getGroupId();
        if (!requestId.isEmpty()&&!groupId.isEmpty()){
            GroupPlan groupPlan = requestPlanService.assembleGroupPlan(requestPlan);
            HttpRequest httpRequest = requestPlanService.assembleRequest(requestPlan);
            return (updateById(groupPlan)&&httpRequestService.updateById(httpRequest));
        }
        return false;
    }


    private Long groupPlanHandler(RequestPlan requestPlan) {
        GroupPlan groupPlan = requestPlanService.assembleGroupPlan(requestPlan);
        if (save(groupPlan)){
            return groupPlan.getId();
        }else {
            return null;
        }

    }

    private boolean httpRequestHandler(RequestPlan requestPlan) {
        HttpRequest httpRequest = requestPlanService.assembleRequest(requestPlan);
        return httpRequestService.save(httpRequest);
    }


}
