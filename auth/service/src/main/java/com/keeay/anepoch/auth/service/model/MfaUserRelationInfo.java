package com.keeay.anepoch.auth.service.model;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/6/27 - 14:42
 */
@Data
public class MfaUserRelationInfo implements Serializable {
    private Long id;
    private String appCode;
    private String appName;
    private String userCode;
    private String mfaSecret;
    private String mfaText;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
