package com.keeay.anepoch.auth.biz.auth.strategy;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import io.jsonwebtoken.Claims;

/**
 * @author xiongyu
 */
public interface VerifyJwtStrategy {
    /**
     * 验证并获取用户信息
     *
     * @param tokenBo         tokenBo
     * @param servletPath servletPath
     */
    void verifyAndGetUser(TokenBo tokenBo, String servletPath);
}
