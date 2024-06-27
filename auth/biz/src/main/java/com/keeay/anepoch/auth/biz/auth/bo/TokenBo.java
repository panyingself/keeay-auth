package com.keeay.anepoch.auth.biz.auth.bo;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/17 - 10:23
 */
@Data
public class TokenBo implements Serializable {
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 用户类型(B端/C端)
     */
    private String userType;
    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 是否已经认证过mfa
     */
    private Boolean mfaFlag;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
