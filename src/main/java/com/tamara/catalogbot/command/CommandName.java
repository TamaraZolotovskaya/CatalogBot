package com.tamara.catalogBot.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Emum для хранения возможных команд.
 *
 * @see Command
 */
@RequiredArgsConstructor
@Getter
public enum CommandName {
    /**
     * Команда "/start", используется для начала взаимодействия с ботом.
     */
    START("/start"),

    /**
     * Команда "/addElement", используется для добавления элемента в дерево.
     */
    ADD("/addElement"),

    /**
     * Команда "/removeElement", используется для удаления элемента из дерева.
     */
    REMOVE("/removeElement"),

    /**
     * Команда "/viewTree", используется для просмотра структуры дерева.
     */
    VIEW("/viewTree"),

    /**
     * Команда "/error", используется для сообщения об ошибке при вводе команды.
     */
    ERROR("/error"),

    /**
     * Команда "/help", используется для получения информации о доступных командах.
     */
    HELP("/help");

    /**
     * Строковое представление команды.
     */
    private final String commandName;
}

