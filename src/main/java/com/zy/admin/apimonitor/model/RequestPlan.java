package com.zy.admin.apimonitor.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @program: zy-admin
 * @description:
 * @author: changzhen
 * @create: 2019-08-01 21:28
 **/
@Data
public class RequestPlan {

    private String requestId
            ,groupId
            ,systemName
            ,name
            ,httpMethod
            ,url
            ,frequency
            ,conditionType
            ,conditions
            ,remark;

    private Boolean enable;

    private Date createTime;
}
