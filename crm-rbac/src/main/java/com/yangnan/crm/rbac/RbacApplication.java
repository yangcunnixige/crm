package com.yangnan.crm.rbac;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

@SpringBootApplication
@MapperScan("com.yangnan.crm.rbac.mapper")
@ComponentScan(basePackages = {"com.yangnan.crm"})
public class RbacApplication {
    private static final Logger LOG = LoggerFactory.getLogger(RbacApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RbacApplication.class);
        Environment environment = run.getEnvironment();
        LOG.info("启动成功");
        LOG.info("地址:\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }


}
