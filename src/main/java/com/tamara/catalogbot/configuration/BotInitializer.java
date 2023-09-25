package com.tamara.catalogBot.configuration;

import com.tamara.catalogBot.listener.TelegramBotUpdatesListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Component
public class BotInitializer {

    private final TelegramBotUpdatesListener bot;

    private Logger logger = LoggerFactory.getLogger(BotInitializer.class);

    public BotInitializer(TelegramBotUpdatesListener bot) {
        this.bot = bot;
    }

    @PostConstruct
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            logger.error("Ошибка при регистрации бота: {}", e.getMessage(), e);
        }
    }
}

