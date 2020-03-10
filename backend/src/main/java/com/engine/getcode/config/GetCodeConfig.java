package com.engine.getcode.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by HaiND on 2/16/2020 4:38 PM.
 */
@Configuration
@EnableTransactionManagement
public class GetCodeConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
