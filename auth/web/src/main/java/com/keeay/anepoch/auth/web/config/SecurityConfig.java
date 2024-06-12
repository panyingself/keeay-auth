package com.keeay.anepoch.auth.web.config;

import com.keeay.anepoch.auth.biz.interfacewhitelistinfo.InterfaceWhiteListInfoBiz;
import com.keeay.anepoch.auth.web.filter.JwtAuthenticationTokenFilter;
import com.keeay.anepoch.base.commons.lang.Safes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description: spring-security配置
 *
 * @author -  pany
 * Time - 2024/4/12 - 17:04
 */
@Configuration
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;
    @Resource
    private InterfaceWhiteListInfoBiz interfaceWhiteListInfoBiz;
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private CorsFilter corsFilter;

    /**
     * Override this method to configure the {@link HttpSecurity}. Typically subclasses
     * should not invoke this method by calling super as it may override their
     * configuration. The default configuration is:
     *
     * <pre>
     * http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
     * </pre>
     * 配置http请求security规则(主)
     *
     * @param httpSecurity the {@link HttpSecurity} to modify
     * @throws Exception if an error occurs
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 注解标记允许匿名访问的url
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = httpSecurity.authorizeRequests();

        List<String> whiteList = interfaceWhiteListInfoBiz.getAllWhiteList();
        //载入注解url列表
        Safes.of(whiteList).forEach(interfaceUri -> {
            log.info("spring security配置文件加载白名单列表 -> interfaceUri - 【 {} 】 ", interfaceUri);
            registry.antMatchers(interfaceUri.trim()).permitAll();
        });

        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf().disable()
                // 认证失败处理类
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 过滤请求
                .authorizeRequests()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/").permitAll()
                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
        // 添加JWT filter(用户名密码认证filter之前添加jwt认证filter)
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
    }

    /**
     * Override this method to configure {@link WebSecurity}. For example, if you wish to
     * ignore certain requests.
     * 忽略某些web请求
     *
     * @param web web
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //忽略swagger
        web.ignoring().antMatchers("/v3/api-docs/**", "/**/swagger*/**");
        web.ignoring().antMatchers("/v2/api-docs/**", "/**/swagger*/**");
        //忽略静态资源
        web.ignoring().antMatchers("/**/*.html", "/**/*.css", "/**/*.js");
        //忽略其他资源
        web.ignoring().antMatchers("/webjars/**", "/druid/**", "/**/*.ico", "/error");
    }
}
