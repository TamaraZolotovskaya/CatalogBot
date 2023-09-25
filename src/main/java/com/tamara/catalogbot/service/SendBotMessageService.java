package com.tamara.catalogBot.service;

/**
 * Service for sending messages via telegram-bot.
 */
public interface SendBotMessageService {

    /**
     * Send message via telegram bot.
     *
     * @param chatId  provided chatId in which messages would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(Long chatId, String message);
}
