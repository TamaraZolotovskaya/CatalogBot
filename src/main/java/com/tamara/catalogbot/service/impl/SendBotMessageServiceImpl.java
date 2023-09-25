package com.tamara.catalogBot.service.impl;

import com.tamara.catalogBot.listener.TelegramBotUpdatesListener;
import com.tamara.catalogBot.service.SendBotMessageService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final TelegramBotUpdatesListener bot;

    public SendBotMessageServiceImpl(TelegramBotUpdatesListener bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
