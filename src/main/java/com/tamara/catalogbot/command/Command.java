package com.tamara.catalogBot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Командный интерфейс для обработки команд Telegram-бота.
 */
public interface Command {

    /**
      * Основной метод, выполняющий логику команды.
      *
      * @param update объект {@link Update}, содержит информацию о сообщении пользователя.
      */
    void execute(Update update);
}