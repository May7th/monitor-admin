package com.zy.admin.system.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;


import com.zy.admin.system.model.LoginRecord;
import com.zy.admin.system.repository.LoginRecordMapper;
import com.zy.admin.system.utils.results.PageResult;

@Service
public class LoginRecordService {
	
    private final LoginRecordMapper loginRecordMapper;

    public LoginRecordService(LoginRecordMapper loginRecordMapper) {
		this.loginRecordMapper = loginRecordMapper;
	}


	public boolean add(LoginRecord loginRecord) {
        loginRecord.setCreateTime(new Date());
        return loginRecordMapper.insert(loginRecord) > 0;
    }

   
    public PageResult<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String account) {
        Page<LoginRecord> page = new Page<>(pageNum, pageSize);
        List<LoginRecord> records = loginRecordMapper.listFull(page, startDate, endDate, account);
        return new PageResult<>(page.getTotal(), records);
    }
}