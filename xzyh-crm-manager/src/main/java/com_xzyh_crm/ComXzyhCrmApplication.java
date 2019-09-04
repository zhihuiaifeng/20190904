package com_xzyh_crm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com_xzyh_crm.dao")
public class ComXzyhCrmApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ComXzyhCrmApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ComXzyhCrmApplication.class);
    }
}
