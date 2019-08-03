package com.zy.admin.apimonitor.controller;



import com.zy.admin.apimonitor.http.HttpRequestHandler;
import com.zy.admin.apimonitor.model.GroupPlan;
import com.zy.admin.apimonitor.model.HttpRequest;
import com.zy.admin.apimonitor.model.RequestPlan;
import com.zy.admin.apimonitor.quartz.DynamicJobManager;
import com.zy.admin.apimonitor.service.IGroupPlanService;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.JsonResult;
import com.zy.admin.system.utils.results.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * http序列表 前端控制器
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@Controller
@RequestMapping("/monitor/groupPlan")
public class GroupPlanController {

    @Autowired
    private IGroupPlanService groupPlanService;

    @Autowired
    private HttpRequestHandler httpRequestHandler;
    @RequestMapping("/planForm")
    public String addPlan(Model model) {

        return "monitor/plan_form";
    }

    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(RequestPlan requestPlan) {


        if (groupPlanService.addRequestAndPlan(requestPlan)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(RequestPlan requestPlan) {

        if (groupPlanService.updateRequestAndPlan(requestPlan)) {

            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    @RequestMapping
    public String monitor(Model model) {

        return "monitor/monitor_list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public PageResult<RequestPlan> list(Integer page, Integer limit, String searchKey, String searchValue) {
        if (page == null) {
            page = 0;
            limit = 0;
        }
        if (StringUtil.isBlank(searchValue)) {
            searchKey = null;
        }
        return groupPlanService.list(page, limit, true, searchKey, searchValue);
    }
    @ResponseBody
    @RequestMapping("/updateState")
    public JsonResult updateState(Long groupId, Integer state) {

        GroupPlan groupPlan = groupPlanService.getById(groupId);
        DynamicJobManager dynamicJobManager = new DynamicJobManager(groupPlan);
        dynamicJobManager.enable();
        if (true) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }


}

