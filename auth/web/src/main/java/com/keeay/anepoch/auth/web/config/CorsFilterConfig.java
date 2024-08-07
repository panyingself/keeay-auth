//package com.keeay.anepoch.auth.web.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
///**
// * Description:跨域拦截器
// *
// * @Author: zhangdh
// * @Time - 2023/9/5 - 14:46
// */
//@Configuration
//public class CorsFilterConfig {
//    /**
//     * 跨域配置
//     */
//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        // 设置访问源地址
//        config.addAllowedOrigin("*");
//        // 设置访问源请求头
//        config.addAllowedHeader("*");
//        // 设置访问源请求方法
//        config.addAllowedMethod("*");
//        // 有效期 1800秒
//        config.setMaxAge(1800L);
//        // 设置暴露的header
//        config.addExposedHeader("Content-Disposition");
//        // 添加映射路径，拦截一切请求
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        // 返回新的CorsFilter
//        return new CorsFilter(source);
//    }
//}
