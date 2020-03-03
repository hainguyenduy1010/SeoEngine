package com.engine.seo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by HaiND on 2/16/2020 4:38 PM.
 */
@Configuration
@EnableTransactionManagement
public class SeoEngineConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
