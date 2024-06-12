package com.keeay.anepoch.auth.biz.feign;

import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.user.api.request.LoginVerifyFeignRequest;
import com.keeay.anepoch.user.api.response.LoginUserFeignResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/22 - 11:33
 */
@Component
//http 调用
@FeignClient(name = "keeay-user", url = "localhost:8089/keeay-user")
//远程调用
//@FeignClient(value = "digital-auth",contextId = "commonDigitalAuth")
public interface UserFeign {
    /**
     * 检查token是否有效和权限
     *
     * @param userCode    userCode
     * @param servletPath servletPath
     * @return true orElse false
     */
    @PostMapping("/feign/auth/checkUserServletPermission")
    HttpResult<Boolean> checkUserServletPermission(@RequestParam("userCode") String userCode, @RequestParam("servletPath") String servletPath);

    /**
     * 检查用户登录
     *
     * @param loginVerifyFeignRequest loginVerifyFeignRequest
     * @return true orElse false
     */
    @PostMapping("/feign/auth/getAndCheckUserLogin")
    HttpResult<LoginUserFeignResponse> getAndCheckUserLogin(@RequestBody LoginVerifyFeignRequest loginVerifyFeignRequest);
}
