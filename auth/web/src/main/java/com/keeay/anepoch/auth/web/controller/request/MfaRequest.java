package com.keeay.anepoch.auth.web.controller.request;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/8/15 - 18:58
 */
@Data
public class MfaRequest implements Serializable {
    private String jwt;
    private String otpCode;
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
