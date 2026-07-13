package edu.ysu.backend.config;

import edu.ysu.backend.util.FileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    private final String BASE_PATH = FileUtil.getSystemPath();

    @SneakyThrows
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:///"+ BASE_PATH+"avatar/");
        log.info("Operation event recorded");

        registry.addResourceHandler("/food/**")
                .addResourceLocations("file:///"+ BASE_PATH+"food/");
        log.info("Operation event recorded");
    }

}
