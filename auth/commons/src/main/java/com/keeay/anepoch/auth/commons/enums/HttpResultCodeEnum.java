package com.keeay.anepoch.auth.commons.enums;


/**
 * http result 状态枚举
 *
 * @author py
 */
public enum HttpResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),
    /**
     * token失效
     */
    TOKEN_INVALIDATION(401, "用户未登录"),
    /**
     * 没有权限
     */
    PERMISSION_DENIED(403, "权限不足"),
    ;

    private Integer code;
    private String message;

    HttpResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public HttpResultCodeEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public HttpResultCodeEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
