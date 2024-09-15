package com.chat.liveon.config;

import com.chat.liveon.auth.service.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }
}
