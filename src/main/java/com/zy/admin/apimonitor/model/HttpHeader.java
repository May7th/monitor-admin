package com.zy.admin.apimonitor.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class HttpHeader implements Serializable {

private static final long serialVersionUID=1L;

    private Long id;

    private Long requestId;

    private String headerName;

    private String headerValue;

    private LocalDateTime createTime;


}
