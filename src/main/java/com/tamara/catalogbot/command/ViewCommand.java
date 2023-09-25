package com.tamara.catalogBot.command;


import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс, представляющий команду для отображения дерева категорий при вызове команды /viewTree.
 *
 * @see Command
 */
@RequiredArgsConstructor
public class ViewCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;


    /**
     * Метод для выполнения команды /viewTree. Отправляет сообщение, где показано дерево категорий
     *
     * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
     */
    @Override
    public void execute(Update update) {
        String message = categoryService.view();
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}

