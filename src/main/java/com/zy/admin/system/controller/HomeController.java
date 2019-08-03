package com.zy.admin.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zy 
 * @date 2018-08-18 16:58
 */
@Controller()
@RequestMapping("/home")
public class HomeController {

    /**
     * 控制台
     */
    @RequestMapping("/console")
    public String console() {
        return "system/home/console";
    }

    /**
     * 消息弹窗
     */
    @RequestMapping("/message")
    public String message() {
        return "system/tpl/message";
    }

    /**
     * 修改密码弹窗
     */
    @RequestMapping("/password")
    public String password() {
        return "system/tpl/password";
    }

    /**
     * 主题设置弹窗
     */
    @RequestMapping("/theme")
    public String theme() {
        return "system/tpl/theme";
    }

    /**
     * 设置主题
     */
    @RequestMapping("/setTheme")
    public String setTheme(String themeName, HttpServletRequest request) {
        if (null == themeName) {
            request.getSession().removeAttribute("theme");
        } else {
            request.getSession().setAttribute("theme", themeName);
        }
        return "redirect:/";
    }
}
