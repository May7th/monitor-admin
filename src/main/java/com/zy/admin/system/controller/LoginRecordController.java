package com.zy.admin.system.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zy.admin.system.model.LoginRecord;
import com.zy.admin.system.service.LoginRecordService;
import com.zy.admin.system.utils.StringUtil;
import com.zy.admin.system.utils.results.PageResult;

/**
 * 登录日志管理
 **/
@Controller
@RequestMapping("/system/loginRecord")
public class LoginRecordController {
  
    private final LoginRecordService loginRecordService;

    public LoginRecordController(LoginRecordService loginRecordService) {
		this.loginRecordService = loginRecordService;
	}

	@RequestMapping()
    public String loginRecord() {
        return "system/login_record";
    }

    /**
     * 查询所有登录日志
     **/
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<LoginRecord> list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return loginRecordService.list(page, limit, startDate, endDate, account);
    }

}
