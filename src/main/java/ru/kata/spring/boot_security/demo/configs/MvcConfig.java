package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user").setViewName("singleUserJS");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/admin").setViewName("adminJS");
        registry.addViewController("/newuser").setViewName("newuserJS");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("/webjars/")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");


//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
