package com.zy.admin.apimonitor.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.admin.apimonitor.model.RequestLog;

import com.zy.admin.apimonitor.repository.RequestLogMapper;
import com.zy.admin.apimonitor.service.IRequestLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * http请求日志表 服务实现类
 * </p>
 *
 * @author changzhen
 * @since 2019-07-17
 */
@Service
public class RequestLogServiceImpl extends ServiceImpl<RequestLogMapper, RequestLog> implements IRequestLogService {

}
