package com.zy.admin.system.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zy.admin.system.config.support.properties.ShzProperties;
import com.zy.admin.system.model.Authorities;
import com.zy.admin.system.security.support.SecurityUtils;
import com.zy.admin.system.service.AuthoritiesService;
import com.zy.admin.system.utils.StringUtil;

/**
 * 
 * @author zy 
 * @date 2018-08-18 16:58
 */
@Controller
public class MainController extends BaseController {

	private final AuthoritiesService authoritiesService;
	
	@Autowired
	static ShzProperties shzProperties;

	public MainController(AuthoritiesService authoritiesService,ShzProperties shzProperties ) {
		this.authoritiesService = authoritiesService;
		MainController.shzProperties = shzProperties;
	}

	/**
	 * 主页
	 */
	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		List<Authorities> authorities = authoritiesService.listByUserId(getLoginUserId());
	    List<Map<String, Object>> menuTree = getMenuTree(authorities, -1);
        model.addAttribute("menus", menuTree);
		model.addAttribute("loginUser", getLoginUser());
		return "system/index";
	}

	@GetMapping("/login")
	public String loginView() {
		return "login";
	}

	@GetMapping("/me")
	@ResponseBody
	public Object me() {
		return getLoginUser();
	}

    @RequestMapping("/iframe")
    public String error(String url, Model model,HttpServletResponse response) {
        model.addAttribute("url", url);
        return "system/tpl/iframe";
    }

	
	/**
	 * 递归转化树形菜单
	 */
	private List<Map<String, Object>> getMenuTree(List<Authorities> authorities, Integer parentId) {
		List<Map<String, Object>> list = new ArrayList<>();
		for (int i = 0; i < authorities.size(); i++) {
			Authorities temp = authorities.get(i);
			if (temp.getIsMenu() == 0 && parentId == temp.getParentId()) {
				Map<String, Object> map = new HashMap<>();
				map.put("menuName", temp.getAuthorityName());
				map.put("menuIcon", temp.getMenuIcon());
				map.put("menuUrl", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
				map.put("subMenus", getMenuTree(authorities, authorities.get(i).getAuthorityId()));
				list.add(map);
			}
		}
		return list;
	}

}
