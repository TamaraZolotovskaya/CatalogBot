package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Unknown {@link Command}.
 */
public class ErrorCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public ErrorCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }
    public static final String ERROR_MESSAGE = "Неизвестная команда, напиши /help чтобы узнать с какими командами я работаю";

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), ERROR_MESSAGE);
    }
}
