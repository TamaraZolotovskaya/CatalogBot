package com.tamara.catalogBot.listener;

import com.tamara.catalogBot.command.CommandContainer;
import com.tamara.catalogBot.configuration.BotConfig;
import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.impl.SendBotMessageServiceImpl;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tamara.catalogBot.command.CommandName.ERROR;


@Service
public class TelegramBotUpdatesListener extends TelegramLongPollingBot {

    private final BotConfig config;
    private final CommandContainer commandContainer;

    public TelegramBotUpdatesListener(BotConfig config, CategoryService categoryService) {
        this.config = config;
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), categoryService);
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        Pattern commandPattern = Pattern.compile("^/([A-Za-z]+)(\\s+<[^>]+>)*(\\s+<[^>]+>)?$");
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            Matcher matcher = commandPattern.matcher(message);
            if (matcher.matches()) {
                String commandIdentifier = message.split(" ")[0];
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(ERROR.getCommandName()).execute(update);
            }
        }
    }
}
