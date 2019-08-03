package com.zy.admin.apimonitor.controller;


import com.zy.admin.apimonitor.model.GroupPlan;
import com.zy.admin.apimonitor.quartz.DynamicJobManager;
import com.zy.admin.apimonitor.service.IGroupPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * http请求表 前端控制器
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@RestController
@RequestMapping("/apimonitor/httpRequest")
public class HttpRequestController {

    @Autowired
    private IGroupPlanService groupPlanService;

    @PostMapping("test")
    public void start(Long groupId){
        GroupPlan groupPlan = groupPlanService.getById(groupId);
        DynamicJobManager dynamicJobManager = new DynamicJobManager(groupPlan);
        dynamicJobManager.enable();

    }

}

