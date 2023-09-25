package com.tamara.catalogBot.service;

/**
 * Интерфейс, содержащий метод для отправки сообщений через бота.
 */
public interface SendBotMessageService {
    /**
     * Метод отправляет сообщение в указанный чат.
     *
     * @param chatId  Уникальный идентификатор чата.
     * @param message Текст сообщения.
     */
    void sendMessage(Long chatId, String message);
}
