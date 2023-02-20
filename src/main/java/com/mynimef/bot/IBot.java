package com.mynimef.bot;

import com.mynimef.bot.containers.BotMessage;
import com.mynimef.bot.containers.BotFile;

public interface IBot {

    void sendMessage(Long chatId, BotMessage message);
    void sendMessage(Long chatId, String text);
    void editMessage(Long chatId, Integer messageId, BotMessage message);
    void sendDoc(Long chatId, BotFile file);
}
