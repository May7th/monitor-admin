package com.zy.admin.apimonitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.admin.apimonitor.model.HttpRequest;


/**
 * <p>
 * http请求表 服务类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
public interface IHttpRequestService extends IService<HttpRequest> {

    HttpRequest getByGroupId(Long groupId);
}
