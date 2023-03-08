package com.mynimef.bot.actions;

public interface ISaveLogs {
    void log(
            String message,
            String chatId,
            Integer messageId,
            String username,
            String firstName,
            String lastName
    );
}
