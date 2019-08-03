package com.zy.admin.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zy.admin.system.model.Authorities;
import com.zy.admin.system.model.Role;
import com.zy.admin.system.service.AuthoritiesService;
import com.zy.admin.system.service.RoleService;
import com.zy.admin.system.service.UserService;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.JsonResult;
import com.zy.admin.system.utils.results.PageResult;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {
	
	private final UserService userService;
	private final RoleService roleService;
	private final AuthoritiesService authoritiesService;

	public RoleController(UserService userService, RoleService roleService, AuthoritiesService authoritiesService) {
		this.userService = userService;
		this.roleService = roleService;
		this.authoritiesService = authoritiesService;
	}




	@RequestMapping()
	public String role() {
		return "system/role";
	}
	
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Role> list(String keyword) {
        List<Role> list = roleService.list(false);
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = String.valueOf(next.getRoleId()).contains(keyword) || next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return new PageResult<>(list);
    }
    
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Role role) {
        if (roleService.add(role)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }
    
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Role role) {
        if (roleService.update(role)) {
            return JsonResult.ok("修改成功！");
        } else {
            return JsonResult.error("修改失败！");
        }
    }
    
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer roleId) {
        if (roleService.updateState(roleId, 1)) {
            return JsonResult.ok("删除成功");
        }
        return JsonResult.error("删除失败");
    }
    
    /**
     * 角色权限树
     */
    @ResponseBody
    @GetMapping("/authTree")
    public List<Map<String, Object>> authTree(Integer roleId) {
        List<Authorities> roleAuths = authoritiesService.listByRoleId(roleId);
        List<Authorities> allAuths = authoritiesService.list();
        List<Map<String, Object>> authTrees = new ArrayList<>();
        for (Authorities one : allAuths) {
        	 Map<String, Object> authTree = new HashMap<>();
        	  authTree.put("id", one.getAuthorityId());
        	  authTree.put("name", one.getAuthorityName() + " " + StringUtil.getStr(one.getAuthority()));
        	  authTree.put("pId", one.getParentId());
        	  authTree.put("open", true);
        	  authTree.put("checked", false);
        	  for (Authorities temp : roleAuths) {
        		  if (temp.getAuthorityId().equals(one.getAuthorityId())) {
                      authTree.put("checked", true);
                      break;
                  }
        	  }	  
        	  authTrees.add(authTree);
        }
        return authTrees;
    }
    
    @ResponseBody
    @PostMapping("/updateRoleAuth")
    public JsonResult updateRoleAuth(Integer roleId, String authIds) {
    	   ObjectMapper mapper = new ObjectMapper();
    	 List<Integer> list;
		try {
			list = mapper.readValue(authIds,List.class);
		} catch (IOException e) {
			 return JsonResult.error("修改失败");
		}
        if (authoritiesService.updateRoleAuth(roleId,list)) {
        	userService.selectByRoleId(roleId).forEach(x -> invalidateSession(x));
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

}
