package com.jobis.config;

import com.jobis.common.utils.AES256;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.jobis.common.utils")
public class CommonConfig {

    @Bean
    public AES256 aes256(){
        return new AES256();
    }
}
