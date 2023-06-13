package com.yangnan.crm.oss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableOpenApi
@ComponentScan(basePackages = {"com.yangnan.crm"})
public class OssApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OssApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(OssApplication.class);
        Environment environment = run.getEnvironment();
        LOG.info("启动成功");
        LOG.info("地址:\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }


}