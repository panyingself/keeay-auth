package com.keeay.anepoch.auth.biz.feign.adapter;

import com.keeay.anepoch.auth.biz.feign.UserFeign;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import com.keeay.anepoch.user.api.request.LoginVerifyFeignRequest;
import com.keeay.anepoch.user.api.response.LoginUserFeignResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/22 - 11:33
 */
@Component
@Slf4j
public class UserFeignAdapter {
    @Resource
    private UserFeign userFeign;

    /**
     * 检查token是否有效和权限
     *
     * @param userCode    userCode
     * @param servletPath servletPath
     * @return true orElse false
     */
    public Boolean checkUserServletPermission(String userCode, String servletPath) {
        log.info("checkUserServletPermission feign invoke start.......");
        HttpResult<Boolean> httpResult = null;
        try {
            httpResult = userFeign.checkUserServletPermission(userCode, servletPath);
            log.info("checkUserServletPermission feign invoke end....... httpResult : {}", httpResult);
            return httpResult.getData();
        } catch (Exception e) {
            log.error("checkUserServletPermission feign invoke exception....... ", e);
        }
        return false;
//        return httpResult.getData();
    }

    /**
     * 检查token是否有效和权限
     *
     * @param loginVerifyFeignRequest loginVerifyFeignRequest
     * @return true orElse false
     */
    public LoginUserFeignResponse checkUserLogin(LoginVerifyFeignRequest loginVerifyFeignRequest) {
        log.info(" {} ", JsonMoreUtils.toJson(loginVerifyFeignRequest));
        HttpResult<LoginUserFeignResponse> httpResult = userFeign.getAndCheckUserLogin(loginVerifyFeignRequest);
        return httpResult.getData();
    }
}
