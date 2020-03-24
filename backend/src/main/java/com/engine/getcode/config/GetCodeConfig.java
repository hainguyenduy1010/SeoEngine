package com.engine.getcode.config;

import com.engine.getcode.GetCodeApplication;
import org.modelmapper.ModelMapper;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by HaiND on 2/16/2020 4:38 PM.
 */
@Configuration
@EnableTransactionManagement
public class GetCodeConfig extends SpringBootServletInitializer {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(GetCodeApplication.class);
    }
}
