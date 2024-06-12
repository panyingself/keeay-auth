package com.keeay.anepoch.auth.commons.enums;


/**
 * http result 状态枚举
 *
 * @author py
 */
public enum UserSystemEnum {
    /**
     * USER
     */
    USER(1, "USER"),
    /**
     * CUSTOMER
     */
    CUSTOMER(2, "CUSTOMER"),
    ;

    private Integer code;
    private String message;

    UserSystemEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public UserSystemEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public UserSystemEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
