package com.tamara.catalogBot.command;

/**
 * Enumeration for {@link Command}'s.
 */
public enum CommandName {
    START("/start"),
    ADD("/addElement"),
    REMOVE("/removeElement"),
    VIEW("/viewTree"),
    ERROR("/error"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}

