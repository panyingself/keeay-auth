package com.keeay.anepoch.auth.biz.auth.helper;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.biz.feign.adapter.UserFeignAdapter;
import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.InterfaceWhiteListInfoBiz;
import com.keeay.anepoch.auth.commons.enums.HttpResultCodeEnum;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import com.keeay.anepoch.base.commons.lang.Safes;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import com.keeay.anepoch.redis.component.utils.RedisStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/4/19 - 16:29
 */
@Component
@Slf4j
public class AuthHelper {
    @Resource
    private InterfaceWhiteListInfoBiz interfaceWhiteListInfoBiz;
    @Resource
    private UserFeignAdapter userFeignAdapter;
    @Resource
    private RedisStringUtils redisStringUtils;
    private static final String REDIS_LOGIN_USER_KEY = "login_user_";

    /**
     * 白名单校验
     *
     * @param servletPath servletPath
     * @return contain true orElse false
     */
    public Boolean checkWhiteList(String servletPath) {
        log.info("checkWhiteList servletPath : 【 {} 】 白名单检查开始", servletPath);
        //查询白名单
        List<String> whiteList = interfaceWhiteListInfoBiz.getAllWhiteList();
        //校验白名单
        if (Safes.of(whiteList).contains(servletPath)) {
            log.info("checkWhiteList servletPath : 【 {} 】 处于白名单", servletPath);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 校验用户是否拥有接口权限
     *
     * @param userCode    userCode
     * @param servletPath servletPath
     * @return has true orElse false
     */
    public void checkUserServletPermission(String userCode, String servletPath) {
        Boolean hasServletPermission = userFeignAdapter.checkUserServletPermission(userCode, servletPath);
        ConditionUtils.checkArgument(hasServletPermission, ErrorCode.of(HttpResultCodeEnum.PERMISSION_DENIED.getCode(), HttpResultCodeEnum.PERMISSION_DENIED.getMessage()));

    }

    /**
     * 校验MFA
     *
     * @param redisLoginUser redisLoginUser
     */
    public void checkMfaCondition(TokenBo redisLoginUser) {
        if (redisLoginUser.getMfaFlag()) {
            log.info("MFA认证通过,continue");
            return;
        }
        log.error("MFA未认证，强制访问失败,userCode : {}", redisLoginUser.getUserCode());
        throw new BizException(ErrorCode.of(HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage()));
    }

    public void saveRedisLoginUser(TokenBo tokenBo) {
        redisStringUtils.setForExpire(REDIS_LOGIN_USER_KEY + tokenBo.getUserCode(), JsonMoreUtils.toJson(tokenBo), 1800L, TimeUnit.SECONDS);
    }

    public TokenBo getRedisLoginUser(String userCode) {
        Object o = redisStringUtils.get(REDIS_LOGIN_USER_KEY + userCode);
        if (Objects.isNull(o)) {
            return null;
        }
        return JsonMoreUtils.toBean(o.toString(), TokenBo.class);
    }
}
