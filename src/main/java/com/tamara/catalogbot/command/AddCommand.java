package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс, представляющий команду для добавления категорий при вызове команды /addElement.
 *
 * @see Command
 */
@RequiredArgsConstructor
public class AddCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;

    /**
     * Метод для выполнения команды /addElement.
     * Отправляет сообщение о добавлении элемента или об ошибке, если пользователь неверно оформил команду
     *
     * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
     */
    @Override
    public void execute(Update update) {
        String message;
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText().trim();
        String[] commandArray = text.replaceAll("\\s+", " ").split("\\s+<");
        if (commandArray.length == 2) {
            //если добавляем корневой элемент
            String category = StringUtils.substring(commandArray[1], 0, commandArray[1].length() - 1);
            message = categoryService.addRoot(category, chatId);
        } else if (commandArray.length == 3) {
            //если добавляем дочерний элемент к родительскому
            String child = StringUtils.substring(commandArray[1], 0, commandArray[1].length() - 1);
            String parent = StringUtils.substring(commandArray[2], 0, commandArray[2].length() - 1);
            message = categoryService.add(child, parent, chatId);
        } else {
            message = "Вы неверно оформили команду /addElement";
        }
        sendBotMessageService.sendMessage(chatId, message);
    }
}
