package com.mynimef.bot.actions;

import com.mynimef.bot.IBot;

public interface ICallback {
    void callback(
            String chatId,
            String messageId,
            String message,
            String username,
            String firstName,
            String lastName,
            IBot bot
    );
}
