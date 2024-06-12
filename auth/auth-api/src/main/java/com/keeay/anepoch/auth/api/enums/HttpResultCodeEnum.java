package com.keeay.anepoch.auth.api.enums;


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
    TOKEN_INVALIDATION(401, "token失效"),
    /**
     * 没有权限
     */
    PERMISSION_DENIED(403, "访问被拒绝"),
    /**
     * 没有权限
     */
    NET_ERROR(502, "服务器开小差啦~~~")
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
