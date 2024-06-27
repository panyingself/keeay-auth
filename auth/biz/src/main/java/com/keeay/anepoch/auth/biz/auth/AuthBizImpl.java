package com.keeay.anepoch.auth.biz.auth;

import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.biz.auth.helper.AuthHelper;
import com.keeay.anepoch.auth.biz.auth.helper.JwtHelper;
import com.keeay.anepoch.auth.biz.auth.strategy.VerifyJwtStrategy;
import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.InterfaceWhiteListInfoBiz;
import com.keeay.anepoch.auth.commons.constant.VerifyUserTypeConstants;
import com.keeay.anepoch.auth.commons.enums.HttpResultCodeEnum;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import com.keeay.anepoch.base.commons.monitor.BaseBizTemplate;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
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
 * Time - 2024/4/15 - 15:38
 */
@Component
@Slf4j
public class AuthBizImpl implements AuthBiz {
    @Resource
    private InterfaceWhiteListInfoBiz interfaceWhiteListInfoBiz;
    @Resource
    private JwtHelper jwtHelper;
    @Resource
    private AuthHelper authHelper;
    @Resource
    private Map<String, VerifyJwtStrategy> verifyJwtStrategyMap;

    /**
     * 验证token并获取用户信息
     *
     * @param jwt jwt
     */
    @Override
    public LoginUser verifyAndGetUser(String jwt, String servletPath) {
        return new BaseBizTemplate<LoginUser>() {
            @Override
            protected LoginUser process() {
                //未登录的请求处理
                if (StringUtils.isBlank(jwt)) {
                    verifyUserNotLogin(servletPath);
                    return new LoginUser();
                }
                //已登录的请求处理
                TokenBo tokenBo = verifyUserLogin(jwt, servletPath);
                return new LoginUser(tokenBo.getUserName(), tokenBo.getUserCode());
            }
        }.execute();
    }

    /**
     * 用户未登录
     *
     * @param servletPath (请求路径)
     */
    private void verifyUserNotLogin(String servletPath) {
        Boolean isWhiteList = authHelper.checkWhiteList(servletPath);
        ConditionUtils.checkArgument(isWhiteList, ErrorCode.of(HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage()));
    }

    /**
     * 用户登录0
     * 验证token有效性
     * 验证token是否失效
     * 验证白名单
     * 验证接口权限
     *
     * @param jwt         jwt
     * @param servletPath servletPath
     */
    private TokenBo verifyUserLogin(String jwt, String servletPath) {
        //校验jwt有效性
        TokenBo tokenBo = jwtHelper.getTokenBo(jwt);
        //权限校验
        VerifyJwtStrategy verifyJwtStrategy = verifyJwtStrategyMap.get(VerifyUserTypeConstants.VERIFY_PREFIX + tokenBo.getUserType().toLowerCase());
        ConditionUtils.checkArgument(Objects.nonNull(verifyJwtStrategy), ErrorCode.of(HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage()));
        verifyJwtStrategy.verifyAndGetUser(tokenBo, servletPath);
        return tokenBo;
    }


}
