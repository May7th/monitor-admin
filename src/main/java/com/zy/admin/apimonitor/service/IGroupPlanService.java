package com.zy.admin.apimonitor.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.zy.admin.apimonitor.model.GroupPlan;
import com.zy.admin.apimonitor.model.RequestPlan;
import com.zy.admin.system.utils.results.PageResult;

/**
 * <p>
 * http序列表 服务类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
public interface IGroupPlanService extends IService<GroupPlan> {

    Boolean addRequestAndPlan(RequestPlan requestPlan);

    PageResult<RequestPlan> list(int pageNum, int pageSize, boolean showDelete, String column, String value);


    Boolean updateRequestAndPlan(RequestPlan requestPlan);
}
