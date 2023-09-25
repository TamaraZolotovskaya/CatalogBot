package com.tamara.catalogBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
public class BotConfig {
    @Value("${botName}")
    private String name;
    @Value("${botToken}")
    private String token;

}
