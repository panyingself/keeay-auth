package com.keeay.anepoch.auth.api.feign;

import com.keeay.anepoch.auth.api.context.LoginUser;
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
//http 调用
@FeignClient(name = "keeay-auth", url = "localhost:8087/keeay-auth")
//nacos 远程调用
//@FeignClient(value = "keeay-auth")
public interface AuthFeign {
    /**
     * 检查token是否有效和权限
     *
     * @param jwt         jwt
     * @param servletPath servletPath
     * @return LoginUser
     */
    @GetMapping("/feign/identify/checkInterfacePermission")
    HttpResult<LoginUser> checkInterfacePermission(@RequestParam(value = "jwt", required = false) String jwt, @RequestParam("servletPath") String servletPath);
}
