package com.keeay.anepoch.auth.biz.feign;

import com.keeay.anepoch.base.commons.base.result.HttpResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/22 - 11:33
 */
@Component
@FeignClient(name = "keeay-customer",url = "localhost:8088/keeay-customer")
//远程调用
//@FeignClient(value = "digital-auth",contextId = "commonDigitalAuth")
public interface CustomerFeign {
    /**
     * 登录验证
     *
     * @param jwt         jwt
     * @param servletPath servletPath
     * @return LoginUser
     */
    @GetMapping("/feign/auth/login")
    HttpResult<Boolean> checkUserServletPermission(@RequestParam("jwt") String jwt, @RequestParam("servletPath") String servletPath);
}
