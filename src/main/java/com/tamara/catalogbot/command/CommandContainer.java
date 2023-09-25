package com.tamara.catalogBot.command;

import com.tamara.catalogBot.service.CategoryService;
import com.tamara.catalogBot.service.SendBotMessageService;

import java.util.Map;

import static com.tamara.catalogBot.command.CommandName.*;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {
    private final Map<String, Command> commandMap;
    private final Command errorCommand;

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

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, errorCommand);
    }

}
