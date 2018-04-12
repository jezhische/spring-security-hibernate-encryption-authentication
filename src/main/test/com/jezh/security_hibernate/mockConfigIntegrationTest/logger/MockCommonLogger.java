package com.jezh.security_hibernate.mockConfigIntegrationTest.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MockCommonLogger {
    @Bean
    public Logger log() {
        return LogManager.getLogger(this.getClass().getName());}
}
