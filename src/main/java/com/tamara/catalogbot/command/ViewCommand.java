package com.tamara.catalogBot.command;


import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ViewCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final CategoryService categoryService;

    public ViewCommand(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
        this.sendBotMessageService = sendBotMessageService;
        this.categoryService = categoryService;
    }

    @Override
    public void execute(Update update) {
        String message = categoryService.view();
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), message);
    }
}

