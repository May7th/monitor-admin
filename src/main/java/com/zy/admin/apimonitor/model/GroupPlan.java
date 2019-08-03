package com.zy.admin.apimonitor.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * http序列表
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GroupPlan implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 所属系统
     */
    private String systemName;

    /**
     * 类型（SINGLE, SEQUENCE）
     */
    private String type  = "SINGLE";

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * job名称
     */
    private String jobName;

    /**
     * 是否启动监控（0-不启动，1-启动）
     */
    private Boolean enabled;

    /**
     * 监控频率，默认THIRTY30秒
     */
    private String frequency;


    /**
     * 存档（0-有效，1-删除）
     */
    private Boolean archived;


}
