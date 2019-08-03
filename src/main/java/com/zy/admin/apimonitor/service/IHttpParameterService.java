package com.zy.admin.apimonitor.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.admin.apimonitor.model.HttpParameter;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author changzhen
 * @since 2019-06-24
 */
public interface IHttpParameterService extends IService<HttpParameter> {

    List<HttpParameter> getListByRequestId(Long requestId);
}
