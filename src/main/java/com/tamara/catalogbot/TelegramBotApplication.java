package com.tamara.catalogBot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Этот класс является точкой входа в приложение Telegram бота и содержит метод {@link #main(String[])}, который запускает
 * приложение.
 */
@SpringBootApplication
public class TelegramBotApplication {

    /**
     * Метод для запуска приложения.
     *
     * @param args аргументы командной строки.
     */
    public static void main(String[] args) {
        SpringApplication.run(TelegramBotApplication.class, args);
    }

}
