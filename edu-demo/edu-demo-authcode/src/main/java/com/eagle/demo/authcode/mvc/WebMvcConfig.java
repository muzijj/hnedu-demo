package com.eagle.demo.authcode.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lijie
 * @create 2020-06-10
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }


}
