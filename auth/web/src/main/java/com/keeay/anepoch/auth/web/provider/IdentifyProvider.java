package com.keeay.anepoch.auth.web.provider;

import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.biz.auth.AuthBiz;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/22 - 11:44
 */
@RestController
@RequestMapping("feign/identify")
public class IdentifyProvider {
    @Resource
    private AuthBiz authBiz;

    /**
     * 校验接口权限
     *
     * @return success true orElse false
     */
    @GetMapping("checkInterfacePermission")
    public HttpResult<LoginUser> checkInterfacePermission(@RequestParam(value = "jwt", required = false) String jwt, @RequestParam("servletPath") String servletPath) {
        LoginUser loginUser = authBiz.verifyAndGetUser(jwt, servletPath);
        return HttpResult.success(loginUser);
    }
}
