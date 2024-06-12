package com.keeay.anepoch.auth.web.filter;


import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.api.context.UserContext;
import com.keeay.anepoch.auth.biz.auth.AuthBiz;
import com.keeay.anepoch.auth.biz.auth.helper.JwtHelper;
import com.keeay.anepoch.auth.commons.enums.HttpResultCodeEnum;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author py
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private AuthBiz authBiz;

    /**
     * 对http请求进行jwt认证拦截
     *
     * @param request  request
     * @param response response
     * @param chain    chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        log.info("=========================security JwtAuthenticationTokenFilter==============================");
        try {
            //如果为feign请求无需校验
            if (isFeignRequest(request)) {
                log.warn("feign接口调用,当前无需校验");
                chain.doFilter(request, response);
                return;
            }
            //开始认证(对于处于白名单的接口,直接放过，但如果是已经登录的用户，需要设置用户信息在上下文)
            LoginUser loginUser = authBiz.verifyAndGetUser(this.getToken(request), request.getServletContext().getContextPath() + request.getServletPath());
            //设置用户登录信息上下文
            UserContext.setUser(loginUser);
            chain.doFilter(request, response);
        } catch (BizException e) {
            log.warn("jwt auth 认证警告,认证失败", e);
            createErrorResponse(response, e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorCode().getCode());
        } catch (Exception e) {
            log.warn("jwt auth 认证警告,认证失败401", e);
            createErrorResponse(response, HttpResultCodeEnum.TOKEN_INVALIDATION.getCode(), HttpResultCodeEnum.TOKEN_INVALIDATION.getMessage(), HttpResultCodeEnum.TOKEN_INVALIDATION.getCode());
        } finally {
            UserContext.clear();
        }
    }

    private boolean isFeignRequest(HttpServletRequest request) {
        // 你可以根据请求头或其他标识来判断是否是Feign请求
        // 例如，可以检查请求头中是否包含特定的Feign标识
        String feignHeaderFlag = request.getHeader("X-Feign-Specific-Header");
        return StringUtils.equalsIgnoreCase("Feign-Specific-Value", feignHeaderFlag);
    }

    private void createErrorResponse(HttpServletResponse response, int data, String message, int code) throws IOException {
        response.setStatus(data);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(JsonMoreUtils.toJson(HttpResult.failure(data, message, code)));
        // 允许所有域访问，可以根据需求进行设置
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    /**
     * 获取请求token
     *
     * @param request 请求
     * @return token
     */
    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(token) && token.startsWith(JwtHelper.TOKEN_PREFIX)) {
            token = token.replace(JwtHelper.TOKEN_PREFIX, "");
        }
        log.info("获取当前token为:{}", token);
        return token;
    }
}
