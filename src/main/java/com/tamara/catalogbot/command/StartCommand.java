package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public final static String START_MESSAGE = "Привет, я бот для работы с категориями. " +
            "Чтобы посмотреть, что я умею напиши /help";

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), START_MESSAGE);
    }
}
