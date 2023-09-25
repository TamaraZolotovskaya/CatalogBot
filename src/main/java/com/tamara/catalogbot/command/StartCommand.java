package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;


/**
 * Класс, представляющий команду для отправки приветственного сообщения при вызове команды /start.
 *
 * @see Command
 */
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    /**
     * Приветственное сообщение, которое будет отправлено при вызове команды /start.
     */
    public final static String START_MESSAGE = "Привет, я бот для работы с категориями. " +
            "Чтобы узнать, что я умею, напишите /help";


    /**
     * Метод для выполнения команды /start, отправляет приветственное сообщение
     *
     * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
     */
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), START_MESSAGE);
    }
}

