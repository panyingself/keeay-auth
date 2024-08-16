package com.keeay.anepoch.auth.biz.accredit.processor;

import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.redis.component.utils.RedisStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author -  pany
 * Time - 2024/8/16 - 13:57
 */
@Component
public class AccountLockProcessor {
    private String loginEmail;
    private static final Integer MAX_COUNT_LIMIT_FOR_10MINUTES = 3;
    private static final Long TEN_MINUTES = 600L;
    private static final String LOCK_KEY = "userLoginErrorLock";
    private Integer lockDataErrorCount = 0;


    @Resource
    private RedisStringUtils redisStringUtils;


    public AccountLockProcessor process(String loginEmail) {
        this.loginEmail = loginEmail;
        this.checkParams();
        this.lockDataErrorCount = 0;
        Optional.ofNullable(redisStringUtils.get(this.getRedisKey())).ifPresent(redisCount -> {
            lockDataErrorCount = Integer.parseInt(String.valueOf(redisCount));
        });

        return this;
    }


    /**
     * 校验用户是否被搜24小时
     *
     * @return this
     */
    public AccountLockProcessor checkLockFor10Minutes() {
        ConditionUtils.checkArgument(!Objects.equals(MAX_COUNT_LIMIT_FOR_10MINUTES, lockDataErrorCount), "错误次数达到上线，请10分钟后再试");
        return this;
    }

    /**
     * 锁住用户10分钟
     *
     * @return this
     */
    public AccountLockProcessor lockFor10Minutes() {
        if (lockDataErrorCount < MAX_COUNT_LIMIT_FOR_10MINUTES) {
            redisStringUtils.setForExpire(this.getRedisKey(), lockDataErrorCount + 1, TEN_MINUTES, TimeUnit.SECONDS);
            throw new BizException("用户名/密码错误,请重试,剩余次数 : " + (MAX_COUNT_LIMIT_FOR_10MINUTES - lockDataErrorCount - 1) + "次");
        }
        return this;
    }

    /**
     * 清除lock
     */
    public void cleanLock() {
        redisStringUtils.delete(this.getRedisKey());
    }

    private String getRedisKey() {
        return LOCK_KEY + "_" + this.loginEmail;
    }


    private void checkParams() {
        ConditionUtils.checkArgument(StringUtils.isNotBlank(this.loginEmail), "loginEmail is blank");
    }
}
