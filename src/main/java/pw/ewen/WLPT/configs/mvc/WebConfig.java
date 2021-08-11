package pw.ewen.WLPT.configs.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 全局允许cors
 */
@Configuration
//@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins()
                .allowedMethods("*");
    }
}
