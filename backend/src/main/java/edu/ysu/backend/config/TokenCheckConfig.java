package edu.ysu.backend.config;

import edu.ysu.backend.filter.TokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class TokenCheckConfig implements WebMvcConfigurer {

    @Autowired
    private TokenFilter tokenFilter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("token校验拦截器启动");
        registry.addInterceptor(tokenFilter)
                .addPathPatterns("/user/**/**")
                .addPathPatterns("/nurse/**/**")
                .addPathPatterns("/customer/**/**")
                .addPathPatterns("/bed/**/**")
                .addPathPatterns("/service/**/**")
                .addPathPatterns("/meal/**/**")
                .addPathPatterns("/menue/**/**")
                .excludePathPatterns("/welcome/**/**");
    }
}
