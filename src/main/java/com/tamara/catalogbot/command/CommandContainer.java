package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;

import java.util.Map;

import static com.tamara.catalogBot.command.CommandName.*;

/**Контейнер команд {@link Command}, которые используются для обработки сообщений пользователя.
 * В нем будут храниться объекты наших команд, и по запросу мы можем получать необходимую команду.
 */
public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command errorCommand;

    /**
     *  В конструкторе заполняем мапу, в которой ключ  - это строка имени команды,
     *  а со значение -  это объект команды типа {@link Command}.
     *
     * @param sendBotMessageService
     * @param categoryService
     *
     * @see CommandName
     */
    public CommandContainer(SendBotMessageService sendBotMessageService, CategoryService categoryService) {
        commandMap = Map.ofEntries(
                Map.entry(START.getCommandName(), new StartCommand(sendBotMessageService)),
                Map.entry(ERROR.getCommandName(), new ErrorCommand(sendBotMessageService)),
                Map.entry(HELP.getCommandName(), new HelpCommand(sendBotMessageService)),
                Map.entry(ADD.getCommandName(), new AddCommand(sendBotMessageService, categoryService)),
                Map.entry(VIEW.getCommandName(), new ViewCommand(sendBotMessageService, categoryService)),
                Map.entry(REMOVE.getCommandName(), new RemoveCommand(sendBotMessageService, categoryService))
        );
        errorCommand = new ErrorCommand(sendBotMessageService);
    }

    /**
     * Извлекает конкретную команду {@link Command} по ее имени (например, /start).
     *
     * @param commandIdentifier Имя команды для извлечения {@link CommandName}
     * @return Экземпляр реализации интерфейса {@link Command}, связанной с указанным именем,
     *         или экземпляр {@link ErrorCommand}, если такой команды нет.
     */
    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, errorCommand);
    }
}

