//package com.zy.admin.business.controller;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.zy.admin.business.model.Demo;
//import com.zy.admin.system.utils.StringUtil;
//import com.zy.admin.system.utils.results.JsonResult;
//import com.zy.admin.system.utils.results.PageResult;
//
//@RequestMapping("/test")
//@Controller
//public class DemoController {
//
//	private final DemoService demoService;
//
//	public DemoController(DemoService demoService) {
//		this.demoService = demoService;
//	}
//
//	@RequestMapping
//	@PreAuthorize("@sec.hasPermission('test:view')")
//	public String test(Model model) {
//		return "test/list";
//	}
//
//	@RequestMapping("/editForm")
//	public String editForm(Model model) {
//		return "test/test_form";
//	}
//
//
//	@RequestMapping("/list")
//	@ResponseBody
//	@PreAuthorize("@sec.hasPermission('test:view')")
//	public PageResult<Demo> list(Integer page, Integer limit,String searchKey, String searchValue,String startDate, String endDate) {
//		if (page == null) {
//			page = 0;
//			limit = 0;
//		}
//		if (StringUtil.isBlank(searchValue)) {
//			searchKey = null;
//		}
//		 if (StringUtil.isBlank(startDate)) {
//	            startDate = null;
//	       }
//		 if (StringUtil.isBlank(endDate)) {
//			 endDate = null;
//	       }
//
//		return demoService.list(page, limit ,searchKey, searchValue,startDate,  endDate);
//	}
//
//
//	@PostMapping("/add")
//	@ResponseBody
//	@PreAuthorize("@sec.hasPermission('test:add')")
//	public JsonResult add(Demo demo) {
//		 if (demoService.add(demo)) {
//	            return JsonResult.ok("添加成功");
//	        } else {
//	            return JsonResult.error("添加失败");
//	        }
//	}
//
//	@PostMapping("/update")
//	@ResponseBody
//	@PreAuthorize("@sec.hasPermission('test:edit')")
//    public JsonResult update(Demo demo) {
//        if (demoService.update(demo)) {
//            return JsonResult.ok("修改成功");
//        } else {
//            return JsonResult.error("修改失败");
//        }
//    }
//
//	@PostMapping("/delete")
//	@ResponseBody
//	@PreAuthorize("@sec.hasPermission('test:delete')")
//    public JsonResult delete(Demo demo) {
//        if (demoService.delete(demo)) {
//            return JsonResult.ok("删除成功");
//        } else {
//            return JsonResult.error("删除失败");
//        }
//    }
//
//
//
//
//}
