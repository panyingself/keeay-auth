package com.keeay.anepoch.auth.biz.auth.helper;

import com.keeay.anepoch.auth.biz.auth.bo.TokenBo;
import com.keeay.anepoch.auth.commons.enums.HttpResultCodeEnum;
import com.keeay.anepoch.auth.commons.enums.UserSystemEnum;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.exception.ErrorCode;
import com.keeay.anepoch.base.commons.utils.ConditionUtils;
import com.keeay.anepoch.redis.component.utils.RedisStringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

/**
 * @author xiongyu
 * The most unusual thing is to live an ordinary life well
 */
@Component
@Slf4j
public class JwtHelper {
    /**
     * 令牌自定义标识
     */
    @Value("${token.header}")
    private String header;
    /**
     * 令牌秘钥
     */
    @Value("${token.secret}")
    private String secret;

    /**
     * 令牌有效期（默认30分钟）
     */
    @Value("${token.expireTime}")
    private int expireTime;

    public static final String TOKEN_PREFIX = "Bearer ";
    @Resource
    private RedisStringUtils redisStringUtils;


    /**
     * 生成登录token
     *
     * @param claims         用户信息
     * @param userSystemEnum 用户系统类型
     * @return 令牌
     */
    public String buildLoginToken(Map<String, Object> claims, UserSystemEnum userSystemEnum) {
        claims.put("type", userSystemEnum.name());
        claims.put("expireTime", LocalDateTime.now().plusMinutes(expireTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        return createToken(claims);
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析并获取tokenBo
     *
     * @param token token
     * @return tokenBo
     */
    public TokenBo getTokenBo(String token) {
        Claims claims = this.parseToken(token);
        TokenBo tokenBo = new TokenBo();
        tokenBo.setUserCode(claims.get("userCode").toString());
        tokenBo.setUserName(claims.get("userName").toString());
        tokenBo.setUserType(claims.get("type").toString());
        tokenBo.setExpireTime((Long) claims.get("expireTime"));
        return tokenBo;
    }

    /**
     * 获取请求token
     *
     * @param request request
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        log.info("获取当前token为:{}", token);
        return token;
    }

    /**
     * 校验登录状态是否过期(如果没在redis中，则代表已经过期)
     *
     * @param userCode userCode
     */
    public void checkExpireForRedis(String userCode) {
        Object userCodeFromRedis = redisStringUtils.get(userCode);
        ConditionUtils.checkArgument(Objects.nonNull(userCodeFromRedis), ErrorCode.of(HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage()));
    }

    /**
     * 校验登录状态是否过期(如果没在redis中，则代表已经过期)
     *
     * @param expireTimeStamp expireTime
     */
    public void checkExpireForCreateTime(Long expireTimeStamp) {
        log.info("checkExpireForCreateTime expireTimeStamp : {}", expireTimeStamp);
        long nowTimeStamp = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        if (nowTimeStamp > expireTimeStamp) {
            log.error("jwt expireTime时间超过有效期!!!!, {} ", expireTimeStamp);
            throw new BizException(ErrorCode.of(HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage()));
        }
    }
}
