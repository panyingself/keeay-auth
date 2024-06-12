package com.keeay.anepoch.auth.web.config;

import com.keeay.anepoch.base.commons.base.result.HttpResult;
import com.keeay.anepoch.base.commons.utils.JsonMoreUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author xiognyu
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e)
            throws IOException {
        log.info("【spring-security - AuthenticationEntryPointImpl 认证失败, requestUri - {} 】,e={}", request.getRequestURI(), e.getMessage());
        String msg = "请求访问：{" + request.getRequestURI() + "}，认证失败，无法访问系统资源";
        renderString(response, JsonMoreUtils.toJson(HttpResult.failure(response.getStatus(), msg)));
    }


    private void renderString(HttpServletResponse response, String string) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
