package com.zy.admin.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zy.admin.system.model.Authorities;
import com.zy.admin.system.model.Role;
import com.zy.admin.system.service.AuthoritiesService;
import com.zy.admin.system.service.RoleService;
import com.zy.admin.system.utils.ReflectUtil;
import com.zy.admin.system.utils.results.JsonResult;
import com.zy.admin.system.utils.results.PageResult;

/**
 * 权限管理
 **/
@Controller
@RequestMapping("/system/authorities")
public class AuthoritiesController extends BaseController {

	private final AuthoritiesService authoritiesService;

	private final RoleService roleService;

	public AuthoritiesController(AuthoritiesService authoritiesService, RoleService roleService) {
		this.authoritiesService = authoritiesService;
		this.roleService = roleService;
	}

	@RequestMapping()
	public String authorities(Model model) {
		List<Role> roles = roleService.list(false);
		model.addAttribute("roles", roles);
		return "system/authorities";
	}
	
	 @RequestMapping("editForm")
	    public String editForm(Model model) {
	        List<Authorities> authorities = authoritiesService.listMenu();
	        model.addAttribute("authorities", authorities);
	        return "system/authorities_form";
	    }

	@ResponseBody
	@RequestMapping("/list")
	public PageResult<Map<String, Object>> list(Integer roleId) {
		List<Map<String, Object>> maps = new ArrayList<>();
		List<Authorities> authorities = authoritiesService.list();
		List<Authorities> roleAuths = authoritiesService.listByRoleId(roleId);
		for (Authorities one : authorities) {
			Map<String, Object> map = ReflectUtil.objectToMap(one);
			map.put("checked", 0);
			for (Authorities roleAuth : roleAuths) {
				if (one.getAuthorityId().equals(roleAuth.getAuthorityId())) {
					map.put("checked", 1);
					break;
				}
			}
			maps.add(map);
		}
		return new PageResult<>(maps);
	}
	
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Authorities authorities) {
        if (authoritiesService.add(authorities)) {
            return JsonResult.ok("添加成功");
        }
        return JsonResult.error("添加失败");
    }
    
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Authorities authorities) {
        if (authoritiesService.update(authorities)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }
    
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer authorityId) {
        if (authoritiesService.delete(authorityId)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }

}
