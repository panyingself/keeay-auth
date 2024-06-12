package com.keeay.anepoch.auth.api.filter;

import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @param
 * @author xiongyu
 * @description 预检请求全部放过
 * @date 2023/10/5 15:10
 * @return null
 */
@Component
@WebFilter
public class PreCheckFilter extends OncePerRequestFilter implements Ordered {

    private static final String OPTIONS = "OPTIONS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 检查请求是否为预检请求（OPTIONS请求）
        if (OPTIONS.equals(request.getMethod())) {
            // 预检请求需要特殊处理，允许跨域访问
            // 允许所有域访问，可以根据需求进行设置
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            // 预检请求的缓存时间，单位秒
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        } else {
            // 非预检请求，继续执行过滤链
            filterChain.doFilter(request, response);
        }
    }

    /**
     * 第一个执行
     *
     * @return order
     */
    @Override
    public int getOrder() {
        return -1;
    }
}
