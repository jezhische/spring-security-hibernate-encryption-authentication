package com.jezh.security_hibernate.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommonLogger {
    @Bean
    public Logger log() {
        return LogManager.getLogger(this.getClass().getName());}
}
