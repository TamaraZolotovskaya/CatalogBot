package com.tamara.catalogBot.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * Класс конфигурации бота.
 *
 * Этот класс используется для настройки бота.
 * Он отмечен аннотацией {@code @Configuration}, что позволяет Spring использовать
 * его для конфигурации бинов в контексте приложения.
 *
 */
@Configuration
@Data
public class BotConfig {
    /**
     * Имя бота.
     *
     * Значение этого поля берется из конфигурационного файла (например, application.properties)
     * с использованием аннотации {@code @Value("${botName}")}.
     */
    @Value("${botName}")
    private String name;

    /**
     * Токен доступа бота.
     *
     * Значение этого поля берется из конфигурационного файла (например, application.properties)
     * с использованием аннотации {@code @Value("${botToken}")}.
     */
    @Value("${botToken}")
    private String token;
}

