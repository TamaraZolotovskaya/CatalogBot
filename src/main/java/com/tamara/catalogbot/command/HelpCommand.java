package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    public final static String HELP_MESSAGE = "Вот какие команды для работы с деревом категорий я выполняю:\n\n" +
            "добавить корневой элемент:\n" +
            "/addElement &lt;название элемента>\n\n" +

            "добавить дочерний элемент к существующему элементу:\n" +
            "/addElement &lt;родительский элемент> &lt;дочерний элемент>\n\n" +


            "просмотр всего дерева категорий:\n" +
            "/viewTree\n\n" +

            "удалить элемент и все его дочерние элементы:\n" +
            "/removeElement &lt;название элемента>\n\n" +

            "список всех доступных команд и краткое их описание:\n" +
            "/help";


    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}

