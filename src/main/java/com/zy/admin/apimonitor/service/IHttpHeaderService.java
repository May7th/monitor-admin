package com.zy.admin.apimonitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.admin.apimonitor.model.HttpHeader;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-24
 */
public interface IHttpHeaderService extends IService<HttpHeader> {

    List<HttpHeader> getListByRequestId(Long requestId);
}
