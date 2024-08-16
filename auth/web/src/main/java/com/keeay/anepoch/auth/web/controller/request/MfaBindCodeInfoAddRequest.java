package com.keeay.anepoch.auth.web.controller.request;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/8/16 - 14:47
 */
@Data
public class MfaBindCodeInfoAddRequest {
    private String appCode;
    private String appName;
    private String userCode;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
