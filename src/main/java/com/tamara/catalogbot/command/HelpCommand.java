package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс, представляющий команду для отправки сообщения при вызове команды /help.
 *
 * @see Command
 */
@RequiredArgsConstructor
public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    /**
     * Сообщение, которое будет отправлено при вызове команды /help.
     */
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


    /**
     * Метод для выполнения команды /help, отправляет сообщение со списком всех доступных команд
     *
     * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
     */
    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}

