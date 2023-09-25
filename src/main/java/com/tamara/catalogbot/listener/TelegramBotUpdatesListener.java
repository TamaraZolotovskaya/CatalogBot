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

/**
 * Класс, отвечающий за обработку сообщений пользователя
 */
@Service
public class TelegramBotUpdatesListener extends TelegramLongPollingBot {

    private final BotConfig config;
    private final CommandContainer commandContainer;

    /**
     * Конструктор класса.
     *
     * @param config          экземпляр класса {@link BotConfig}
     * @param categoryService экземпляр класса {@link CommandContainer}
     */
    public TelegramBotUpdatesListener(BotConfig config, CategoryService categoryService) {
        this.config = config;
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), categoryService);
    }

    /**
     * Получение имени бота.
     *
     * @return имя бота.
     */
    @Override
    public String getBotUsername() {
        return config.getName();
    }

    /**
     * Получение токена бота.
     *
     * @return токен бота.
     */
    @Override
    public String getBotToken() {
        return config.getToken();
    }


    /**
     * Метод для обработки сообщений пользователя.
     * Отвечает на команды пользователя или выводит сообщение об ошибке, если команда не верна.
     *
     * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Создаем регулярное выражение для проверки команды
        Pattern commandPattern = Pattern.compile("^/([A-Za-z]+)(\\s+<[^>]+>)*(\\s+<[^>]+>)?$");

        // Проверяем, есть ли сообщение и содержит ли оно текст
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            Matcher matcher = commandPattern.matcher(message);

            // Проверяем, соответствует ли сообщение формату команды
            if (matcher.matches()) {
                String commandIdentifier = message.split(" ")[0]; // Идентифицируем команду

                // Получаем и выполняем соответствующую команду из контейнера команд
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                // Если сообщение не соответствует формату команды, отправляем сообщение об ошибке
                commandContainer.retrieveCommand(ERROR.getCommandName()).execute(update);
            }
        }
    }
}
