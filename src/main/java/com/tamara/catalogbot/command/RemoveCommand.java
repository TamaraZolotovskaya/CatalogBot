package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

public class RemoveCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;

    public RemoveCommand(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
        this.sendBotMessageService = sendBotMessageService;
        this.categoryService = categoryService;
    }

    @Override
    public void execute(Update update) {
        String message;
        String text = update.getMessage().getText().trim();
        String[] commandArray = text.split("\\s+<");
        if (commandArray.length == 2) {
            String category = StringUtils.substring(commandArray[1], 0, commandArray[1].length() - 1);
            message = categoryService.remove(category);
        } else {
            message = "Вы неверно оформили команду /removeElement";
        }
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}

