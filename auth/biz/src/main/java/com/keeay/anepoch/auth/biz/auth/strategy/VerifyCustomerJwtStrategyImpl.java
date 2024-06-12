package com.keeay.anepoch.auth.biz.auth.strategy;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.commons.constant.VerifyUserTypeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xx
 * The most unusual thing is to live an ordinary life well
 */
@Component(VerifyUserTypeConstants.CUSTOMER)
@Slf4j
public class VerifyCustomerJwtStrategyImpl implements VerifyJwtStrategy {


    /**
     * 验证并获取用户信息
     *
     * @param tokenBo    tokenBo
     * @param servletPath servletPath
     */
    @Override
    public void verifyAndGetUser(TokenBo tokenBo, String servletPath) {

    }
}
