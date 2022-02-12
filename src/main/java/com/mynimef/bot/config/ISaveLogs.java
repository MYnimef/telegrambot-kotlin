package com.mynimef.bot.config;

public interface ISaveLogs {
    void log(String message, Long chatId, String username, String firstName, String lastName);
}
