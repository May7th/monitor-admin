package com.zy.admin.apimonitor.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * http请求表
 * </p>
 *
 * @author changzhen
 * @since 2019-06-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HttpRequest implements Serializable {

private static final long serialVersionUID=1L;

    public enum CheckCondition {
        CONTAINS, DOESNT_CONTAIN, STATUSCODE, DEFAULT
    }
    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /**
     * 父主键（即http_sequence表的guid）
     */
    private Long groupId;

    /**
     * 序号
     */
    private Integer sort = 1;

    /**
     * 地址
     */
    private String url;

    /**
     * HTTP类型（GET, HEAD, POST, PUT, DELETE）
     */
    private String httpMethod;

    /**
     *
     */
    @TableField(exist = false)
    private List<HttpHeader> headers = new ArrayList<>();

    /**
     *
     */
    @TableField(exist = false)
    private List<HttpParameter> parameters = new ArrayList<>();

    /**
     * 最大连接时间
     */
    private Integer maxSeconds;

    /**
     * 结果校验类型（CONTAINS, DOESNT_CONTAIN, STATUSCODE, DEFAULT）
     */
    private CheckCondition conditionType = CheckCondition.DEFAULT;

    /**
     * 结果校验内容
     */
    private String conditions;

    /**
     * 返回结果的格式（XML, JSON）
     */
    private String resultType;

    /**
     * 变量定义，格式（key::value\nkey::value）
     */
    private String variables;

    /**
     * 备注
     */
    private String remark;

    /**
     * 存档（0-有效，1-删除）
     */
    private Boolean archived;

    /**
     * 创建时间
     */
    private Date createTime;


}
