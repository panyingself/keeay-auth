package com.keeay.anepoch.auth.biz.auth.strategy;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.biz.auth.helper.AuthHelper;
import com.keeay.anepoch.auth.biz.auth.helper.JwtHelper;
import com.keeay.anepoch.auth.commons.constant.VerifyUserTypeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author xx
 * The most unusual thing is to live an ordinary life well
 */
@Component(VerifyUserTypeConstants.USER)
@Slf4j
public class VerifyUserJwtStrategyImpl implements VerifyJwtStrategy {
    private static final String TOKEN_REDIS_KEY_HEADER = "user_login_token_";
    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private AuthHelper authHelper;

    /**
     * 验证并获取用户信息
     *
     * @param tokenBo tokenBo
     */
    @Override
    public void verifyAndGetUser(TokenBo tokenBo, String servletPath) {
        //校验过期时间
        jwtHelper.checkExpireForCreateTime(tokenBo.getExpireTime());
        //登录状态和过期时间校验
//        jwtHelper.checkExpireForRedis(TOKEN_REDIS_KEY_HEADER + tokenBo.getUserCode());
        //白名单校验
        Boolean whiteListFlag = authHelper.checkWhiteList(servletPath);
        if (whiteListFlag) {
            return;
        }
        //权限校验
        authHelper.checkUserServletPermission(tokenBo.getUserCode(), servletPath);
    }
}
