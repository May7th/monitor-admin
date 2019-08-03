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
 * http请求日志表
 * </p>
 *
 * @author changzhen
 * @since 2019-07-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RequestLog implements Serializable {

private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父主键（http_sequence表guid）
     */
    private Long groupId;

    /**
     * 父主键（http_request表guid）
     */
    private Long requestId;

    /**
     * 状态（0-失败；1-成功）
     */
    private Boolean status;

    /**
     * 请求耗时
     */
    private Integer costTime;

    /**
     * 响应状态码
     */
    private String statusCode;

    /**
     * 响应结果
     */
    private String responseBody;

    /**
     * 请求日志
     */
    private String log;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
