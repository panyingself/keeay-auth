package com.keeay.anepoch.auth.biz.auth;

import com.keeay.anepoch.auth.api.context.LoginUser;

/**
 * Description: 鉴权/身份验证
 *
 * @author -  pany
 * Time - 2024/4/15 - 11:50
 */
public interface AuthBiz {
    /**
     * 验证token并获取用户信息
     *
     * @param token       token
     * @param servletPath servletPath
     */
    LoginUser verifyAndGetUser(String token, String servletPath);
}
