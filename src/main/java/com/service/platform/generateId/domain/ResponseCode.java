package com.service.platform.generateId.domain;

public enum ResponseCode {

    SUCCESS(1,"success"),
    /**
     * 失败
     */
    FAIL(-1,"fail"),
    /**
     * 错误
     */
    ERROR(-2,"error"),
    /**
     * 参数非法
     */
    PARAMETER_INVALID(1002,"parameter illegal!");

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;

    private ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
