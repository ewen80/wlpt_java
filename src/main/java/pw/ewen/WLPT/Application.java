package pw.ewen.WLPT;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pw.ewen.WLPT.configs.biz.BizConfig;

/**
 * Created by wenliang on 17-1-24.
 */
@SpringBootApplication(scanBasePackages="pw.ewen.WLPT")
@EnableConfigurationProperties(BizConfig.class)
public class Application {
    public static void main(String[] args) throws ClassNotFoundException {
        SpringApplication.run(Application.class, args);
    }
}
