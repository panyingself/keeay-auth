package com.keeay.anepoch.auth.api.context;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/19 - 17:42
 */
@Data
public class LoginUser implements Serializable {
    private String userName;
    private String userCode;

    public LoginUser(String userName, String userCode) {
        this.userName = userName;
        this.userCode = userCode;
    }

    public LoginUser() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
