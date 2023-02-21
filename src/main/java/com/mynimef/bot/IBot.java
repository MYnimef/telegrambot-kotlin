package com.mynimef.bot;

import com.mynimef.bot.containers.BotMessage;
import com.mynimef.bot.containers.BotFile;

public interface IBot {

    void sendMessage(String chatId, BotMessage message);
    void sendMessage(String chatId, String text);
    void editMessage(String chatId, Integer messageId, BotMessage message);
    void sendDoc(String chatId, BotFile file);
}
