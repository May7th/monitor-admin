package com.zy.admin.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zy.admin.system.exception.ParameterException;
import com.zy.admin.system.model.Role;
import com.zy.admin.system.model.User;
import com.zy.admin.system.service.RoleService;
import com.zy.admin.system.service.UserService;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.JsonResult;
import com.zy.admin.system.utils.results.PageResult;

/**
 * 
 * @author zy 
 * @date 2018-08-18 16:58
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {

	private final RoleService roleService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;



	public UserController(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
		this.roleService = roleService;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	@RequestMapping
	@PreAuthorize("@sec.hasPermission('user:view')")
	public String user(Model model) {
		List<Role> roles = roleService.list(false);
		model.addAttribute("roles", roles);
		return "system/user";
	}
	
	 @RequestMapping("/editForm")
	    public String addUser(Model model) {
	        List<Role> roles = roleService.list(false);
	        model.addAttribute("roles", roles);
	        return "system/user_form";
	    }


	@ResponseBody
	@RequestMapping("/list")
	@PreAuthorize("@sec.hasPermission('user:view')")
	public PageResult<User> list(Integer page, Integer limit, String searchKey, String searchValue) {
		if (page == null) {
			page = 0;
			limit = 0;
		}
		if (StringUtil.isBlank(searchValue)) {
			searchKey = null;
		}
		return userService.list(page, limit, true, searchKey, searchValue);
	}
	
	
    @ResponseBody
    @RequestMapping("/add")
    @PreAuthorize("@sec.hasPermission('user:add')")
    public JsonResult add(User user, String roleId) {
        user.setRoles(getRoles(roleId));
        user.setPassword("123456");
        if (userService.add(user)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }
    
    @ResponseBody
    @RequestMapping("/update")
    @PreAuthorize("@sec.hasPermission('user:edit')")
    public JsonResult update(User user, String roleId) {
        user.setRoles(getRoles(roleId));
        if (userService.update(user)) {
        	invalidateSession(userService.getById(user.getUserId()));
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }
    
    @ResponseBody
    @RequestMapping("/updateState")
    @PreAuthorize("@sec.hasPermission('user:delete')")
    public JsonResult updateState(Integer userId, Integer state) {
        if (userService.updateState(userId, state)) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }
    
    @ResponseBody
    @RequestMapping("/restPsw")
    @PreAuthorize("@sec.hasPermission('user:edit')")
    public JsonResult resetPsw(Integer userId) {
        if (userService.updatePsw(userId,"123456")) {
            return JsonResult.ok("重置成功");
        } else {
            return JsonResult.error("重置失败");
        }
    }
    
    
    private List<Role> getRoles(String roleStr) {
        List<Role> roles = new ArrayList<>();
        String[] split = roleStr.split(",");
        for (String t : split) {
            if (t.equals("1")) {
                throw new ParameterException("不能添加超级管理员");
            }
            roles.add(new Role(Integer.parseInt(t)));
        }
        return roles;
    }
    
    /**
     * 修改自己密码
     **/
    @ResponseBody
    @RequestMapping("/updatePsw")
    public JsonResult updatePsw(String oldPsw, String newPsw) {
      /*  if ("admin".equals(getLoginUser().getUsername())) {
            return JsonResult.error("演示账号admin关闭该功能");
        }*/
        
    	//new BCryptPasswordEncoder().matches(rawPassword, encodedPassword)
    	

        if (!passwordEncoder.matches(oldPsw,getLoginUser().getPassword())) {
            return JsonResult.error("原密码输入不正确");
        }
        if (userService.updatePsw(getLoginUserId(), newPsw)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }
	

}
