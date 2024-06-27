package com.keeay.anepoch.auth.biz.accredit;

import com.keeay.anepoch.user.api.request.LoginVerifyFeignRequest;

/**
 * Description: 授权
 *
 * @author -  pany
 * Time - 2024/4/23 - 10:51
 */
public interface AccreditBiz {
    /**
     * 获取B端用户名密码模式jwt
     *
     * @param loginVerifyFeignRequest   loginVerifyFeignRequest
     * @return jwt
     */
    String verifyAndGetJwtForUser(LoginVerifyFeignRequest loginVerifyFeignRequest);

    /**
     * 获取C端用户名密码模式jwt
     *
     * @param userName   userName
     * @param password   password
     * @param verifyCode verifyCode
     * @return jwt
     */
    String verifyAndGetJwtForCustomer(String userName, String password, String verifyCode);
}
