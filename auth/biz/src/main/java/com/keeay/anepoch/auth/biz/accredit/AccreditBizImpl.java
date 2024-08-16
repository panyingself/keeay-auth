package com.keeay.anepoch.auth.biz.accredit;

import com.google.common.collect.Maps;
import com.keeay.anepoch.auth.biz.accredit.processor.AccountLockProcessor;
import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.biz.auth.helper.AuthHelper;
import com.keeay.anepoch.auth.biz.auth.helper.JwtHelper;
import com.keeay.anepoch.auth.biz.feign.adapter.UserFeignAdapter;
import com.keeay.anepoch.auth.commons.enums.UserSystemEnum;
import com.keeay.anepoch.base.commons.exception.BizException;
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
    private AuthHelper authHelper;
    @Resource
    private UserFeignAdapter userFeignAdapter;
    @Resource
    private AccountLockProcessor accountLockProcessor;

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
            protected void checkParam() {
                ConditionUtils.checkArgument(Objects.nonNull(loginVerifyFeignRequest.getType()), "type is blank");
            }

            @Override
            protected String process() {
                // 前置条件校验
                this.preConditionCheck(loginVerifyFeignRequest);
                // 开始认证
                LoginUserFeignResponse loginUserFeignResponse = userFeignAdapter.checkUserLogin(loginVerifyFeignRequest);
                // 认证失败处理
                if (Objects.isNull(loginUserFeignResponse)) {
                    return this.handleForFailure();
                }
                // 认证成功处理
                return this.handleForSuccess(loginUserFeignResponse);

            }

            /**
             * 前置条件校验
             * @param loginVerifyFeignRequest loginVerifyFeignRequest
             */
            private void preConditionCheck(LoginVerifyFeignRequest loginVerifyFeignRequest) {
                // 错误限流
                accountLockProcessor.process(loginVerifyFeignRequest.getAccountVerifyRequest().getLoginName())
                        // 校验10分钟错误限流
                        .checkLockFor10Minutes();
            }

            /**
             * 认证失败结果处理
             * @return result
             */
            private String handleForFailure() {
                accountLockProcessor.process(loginVerifyFeignRequest.getAccountVerifyRequest().getLoginName())
                        // 10分钟错误限流处理
                        .lockFor10Minutes();
                return "";
            }

            /**
             * 认证成功结果处理
             * @param loginUserFeignResponse loginUserFeignResponse
             * @return result
             */
            private String handleForSuccess(LoginUserFeignResponse loginUserFeignResponse) {
                // 登录认证成功,生成jwt返回
                Map<String, Object> claims = JsonMoreUtils.ofMap(JsonMoreUtils.toJson(loginUserFeignResponse), String.class, Object.class);
                String userToken = jwtHelper.buildLoginToken(claims, UserSystemEnum.USER);
                // 设置用户redis信息(30分钟有效)
                TokenBo tokenBo = new TokenBo();
                tokenBo.setUserName(loginUserFeignResponse.getUserName());
                tokenBo.setUserCode(loginUserFeignResponse.getUserCode());
                tokenBo.setUserType(claims.get("type").toString());
                tokenBo.setExpireTime(Long.valueOf(claims.get("expireTime").toString()));
                tokenBo.setMfaFlag(Boolean.valueOf(claims.get("mfaFlag").toString()));
                authHelper.saveRedisLoginUser(tokenBo);
                // 清除lock count
                accountLockProcessor.process(loginVerifyFeignRequest.getAccountVerifyRequest().getLoginName()).cleanLock();
                return userToken;
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
