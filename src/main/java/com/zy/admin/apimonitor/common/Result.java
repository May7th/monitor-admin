package com.zy.admin.apimonitor.common;

import lombok.Data;

/**
 * @program: api-monitor
 * @description:
 * @author: changzhen
 * @create: 2019-06-03 11:04
 **/
@Data
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
