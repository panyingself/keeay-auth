

package com.keeay.anepoch.auth.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author py
 */
@SpringBootApplication
@EnableConfigurationProperties
@MapperScan("com.keeay.anepoch.auth.service.dao")
@ComponentScans(value = {@ComponentScan("com.keeay.anepoch.base.commons"), @ComponentScan("com.keeay.anepoch.redis"), @ComponentScan("com.keeay.anepoch.auth")})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.keeay.anepoch")
@EnableAspectJAutoProxy
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * //为了打包spring-boot项目
     *
     * @param builder builder
     */
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}
