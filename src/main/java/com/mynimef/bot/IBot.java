package com.mynimef.bot;

import com.mynimef.bot.containers.BotMessage;
import com.mynimef.bot.containers.BotFile;

public interface IBot {

    Integer sendMessage(String chatId, BotMessage message);
    Integer sendMessage(String chatId, String text);
    void editMessage(String chatId, Integer messageId, String text);
    void editMessage(String chatId, Integer messageId, BotMessage message);
    void deleteMessage(String chatId, Integer messageId);
    void sendDoc(String chatId, BotFile file);
}
