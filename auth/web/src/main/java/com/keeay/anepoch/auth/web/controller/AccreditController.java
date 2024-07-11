package com.keeay.anepoch.auth.web.controller;

import com.keeay.anepoch.auth.biz.accredit.AccreditBiz;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.user.api.enums.VerifyLoginTypeEnum;
import com.keeay.anepoch.user.api.request.LoginAccountVerifyFeignRequest;
import com.keeay.anepoch.user.api.request.LoginVerifyFeignRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: 提供授权服务
 * 1、授权码模式
 * 2、客户端模式
 * 3、用户名密码模式
 * 4、简化Implicit模式
 *
 * @author -  pany
 * Time - 2024/4/12 - 16:35
 */
@RestController
@RequestMapping("api/accredit")
public class AccreditController {
    @Resource
    private AccreditBiz accreditBiz;

    /**
     * 用户名密码
     *
     * @return 用户授权信息(jwt)
     */
    @PostMapping("/user/accreditForAccount")
    public HttpResult<String> accreditForUserB(@RequestBody LoginAccountVerifyFeignRequest accountVerifyRequest) {
//        return HttpResult.success("abs");
        LoginVerifyFeignRequest loginVerifyFeignRequest = new LoginVerifyFeignRequest();
        loginVerifyFeignRequest.setAccountVerifyRequest(accountVerifyRequest);
        loginVerifyFeignRequest.setType(VerifyLoginTypeEnum.ACCOUNT);
        //invoke
        String jwt = accreditBiz.verifyAndGetJwtForUser(loginVerifyFeignRequest);
        return HttpResult.success(jwt);
    }

    /**
     * 用户名密码
     *
     * @return 用户授权信息(jwt)
     */
    @GetMapping("/customer/accreditForUser")
    public HttpResult<String> accreditForUserC(@RequestParam("userName") String userName, @RequestParam("password") String password, @RequestParam("verifyCode") String verifyCode) {
        String jwt = accreditBiz.verifyAndGetJwtForCustomer(userName, password, verifyCode);
        return HttpResult.success(jwt);
    }
}
