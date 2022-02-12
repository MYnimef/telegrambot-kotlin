package com.mynimef.bot.config;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

final class MessageReceiverNoLogs {
    static void onMessageReceived(Message message, IMessageHandler handler) {
        Chat chat = message.getChat();
        handler.handleMessage(
                message.getText(),
                message.getChatId(),
                chat.getUserName(),
                chat.getFirstName(),
                chat.getLastName()
        );
    }
}
