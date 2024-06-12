package com.keeay.anepoch.auth.api.feign.adapter;

import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.api.enums.HttpResultCodeEnum;
import com.keeay.anepoch.auth.api.feign.AuthFeign;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/22 - 11:33
 */
@Component
@Slf4j
public class AuthFeignAdapter {
    @Resource
    private AuthFeign authFeign;

    /**
     * 校验用户是否拥有servletPath权限，并返回登录用户信息
     *
     * @param jwt         jwt
     * @param servletPath servletPath
     * @return loginUser
     */
    public LoginUser checkPermissionAndGetUser(String jwt, String servletPath) {
        log.debug("invoke checkPermissionAndGetUser feign start,jwt : {} , servletPath : {}", jwt, servletPath);
        HttpResult<LoginUser> httpResult = authFeign.checkInterfacePermission(jwt, servletPath);
        log.debug("invoke checkPermissionAndGetUser feign end , httpResult : {}", httpResult);
        if (!Objects.equals(HttpResultCodeEnum.SUCCESS.getCode(), httpResult.getCode())) {
            throw new BizException(ErrorCode.of(httpResult.getCode(), httpResult.getMessage()));
        }
        return httpResult.getData();
    }
}
