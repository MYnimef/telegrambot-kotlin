package com.mynimef.bot.config;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

final class MessageReceiverLogs {
    static void onMessageReceived(Message message, IMessageHandler handler, ISaveLogs saveLog) {
        String text = message.getText();
        Chat chat = message.getChat();
        Long chatId = message.getChatId();
        String username = chat.getUserName();
        String firstName = chat.getFirstName();
        String lastName = chat.getFirstName();

        saveLog.log(text, chatId, username, firstName, lastName);
        handler.handleMessage(text, chatId, username, firstName, lastName);
    }
}
