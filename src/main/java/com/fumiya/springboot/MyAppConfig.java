package com.fumiya.springboot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyAppConfig {
    @Bean
    MyDataBean myDataBean() {
        return new MyDataBean();
    }
}
