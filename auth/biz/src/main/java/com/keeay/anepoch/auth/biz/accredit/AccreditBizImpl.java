package com.keeay.anepoch.auth.biz.accredit;

import com.google.common.collect.Maps;
import com.keeay.anepoch.auth.biz.auth.helper.JwtHelper;
import com.keeay.anepoch.auth.biz.feign.adapter.UserFeignAdapter;
import com.keeay.anepoch.auth.commons.enums.UserSystemEnum;
import com.keeay.anepoch.base.commons.monitor.BaseBizTemplate;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import com.keeay.anepoch.user.api.request.LoginVerifyFeignRequest;
import com.keeay.anepoch.user.api.response.LoginUserFeignResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/23 - 10:51
 */
@Component
@Slf4j
public class AccreditBizImpl implements AccreditBiz {
    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private UserFeignAdapter userFeignAdapter;

    /**
     * 获取B端用户名密码模式jwt
     *
     * @param loginVerifyFeignRequest loginVerifyFeignRequest
     * @return jwt
     */
    @Override
    public String verifyAndGetJwtForUser(LoginVerifyFeignRequest loginVerifyFeignRequest) {
        log.info("verifyAndGetJwtForUser biz start, loginVerifyFeignRequest : {}", loginVerifyFeignRequest);
        return new BaseBizTemplate<String>() {
            @Override
            protected String process() {
                ConditionUtils.checkArgument(Objects.nonNull(loginVerifyFeignRequest.getType()), "type is blank");
                //查询验证用户信息是否存在
                LoginUserFeignResponse loginUserFeignResponse = userFeignAdapter.checkUserLogin(loginVerifyFeignRequest);
                ConditionUtils.checkArgument(Objects.nonNull(loginUserFeignResponse), "认证信息错误");
                //生成jwt返回
                Map<String, Object> claims = JsonMoreUtils.ofMap(JsonMoreUtils.toJson(loginUserFeignResponse), String.class, Object.class);
                return jwtHelper.buildLoginToken(claims, UserSystemEnum.USER);
            }
        }.execute();
    }

    /**
     * 获取C端用户名密码模式jwt
     *
     * @param userName   userName
     * @param password   password
     * @param verifyCode verifyCode
     * @return jwt
     */
    @Override
    public String verifyAndGetJwtForCustomer(String userName, String password, String verifyCode) {
        log.info("verifyAndGetJwtForCustomer biz start, userName : {},password : {}, verifyCode : {}", userName, password, verifyCode);
        return new BaseBizTemplate<String>() {
            @Override
            protected String process() {
                ConditionUtils.checkArgument(StringUtils.isNotBlank(userName), "userName is blank");
                ConditionUtils.checkArgument(StringUtils.isNotBlank(password), "password is blank");
                ConditionUtils.checkArgument(StringUtils.isNotBlank(verifyCode), "verifyCode is blank");
                //查询验证用户信息是否存在
                //生成jwt返回
                Map<String, Object> claims = Maps.newHashMap();
                claims.put("userName", userName);
                return jwtHelper.buildLoginToken(claims, UserSystemEnum.CUSTOMER);
            }
        }.execute();
    }
}
