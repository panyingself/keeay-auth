package com.keeay.anepoch.auth.api.filter;


import com.keeay.anepoch.auth.api.context.LoginUser;
import com.keeay.anepoch.auth.api.context.UserContext;
import com.keeay.anepoch.auth.api.enums.HttpResultCodeEnum;
import com.keeay.anepoch.auth.api.feign.adapter.AuthFeignAdapter;
import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.exception.BizException;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token过滤器 验证token有效性
 *
 * @author py
 */
@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter implements Ordered {
    public static final String TOKEN_PREFIX = "Bearer";
    @Resource
    private AuthFeignAdapter authFeignAdapter;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("=========================auth JwtAuthenticationTokenFilter start==============================");
        try {
            //如果上下文中存在user，需要放过(代表本次操作被多次执行)
            if (Objects.nonNull(UserContext.getUser())) {
                log.debug("=========================auth JwtAuthenticationTokenFilter fast end,repeat operation==============================");
                chain.doFilter(request, response);
                return;
            }
            //判断需要放过校验的操作
            if (isFeignRequest(request)) {
                log.debug("=========================auth JwtAuthenticationTokenFilter fast end,feign request==============================");
                chain.doFilter(request, response);
                return;
            }
            //统一请求认证中心,鉴别用户身份,权限
            log.debug("=========================auth JwtAuthenticationTokenFilter normal==============================");
            this.auth(request, getToken(request));
            chain.doFilter(request, response);
        } catch (BizException e) {
            // 捕获异常后，在这里处理异常并返回适当的响应
            log.error("auth filter biz exception:", e);
            createErrorResponse(response, e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getErrorCode().getCode());
        } catch (Exception e) {
            // 捕获异常后，在这里处理异常并返回适当的响应(权限出现异常后，统一返回token失效)
            log.error("auth filter runtime exception:", e);
            createErrorResponse(response, HttpResultCodeEnum.NET_ERROR.getCode(), HttpResultCodeEnum.NET_ERROR.getMessage(), HttpResultCodeEnum.NET_ERROR.getCode());
        } finally {
            UserContext.clear();
        }
        log.debug("=========================auth JwtAuthenticationTokenFilter end==============================");
    }

    private void auth(HttpServletRequest request, String token) throws ServletException, IOException {
        log.info("auth filter start...");
        LoginUser loginUser = authFeignAdapter.checkPermissionAndGetUser(token, request.getServletContext().getContextPath() + request.getServletPath());
        UserContext.setUser(loginUser);
        log.info("最后执行 doFilter!");
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
        if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
        }
        log.info("获取当前token为:{}", token);
        return token;
    }

    private boolean isFeignRequest(HttpServletRequest request) {
        // 你可以根据请求头或其他标识来判断是否是Feign请求
        // 例如，可以检查请求头中是否包含特定的Feign标识
        String feignHeader = request.getHeader("X-Feign-Specific-Header");
        return "Feign-Specific-Value".equals(feignHeader);
    }

    /**
     * 第一个执行
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
