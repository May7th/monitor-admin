package com.zy.admin.apimonitor.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author changzhen
 * @since 2019-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class HttpParameter implements Serializable {

private static final long serialVersionUID=1L;

    private Long id;

    private Long requestId;

    private String parameterName;

    private String parameterValue;

    private Date createTime;


}
