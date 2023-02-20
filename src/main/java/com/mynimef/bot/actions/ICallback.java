package com.mynimef.bot.actions;

import com.mynimef.bot.IBot;

public interface ICallback {
    void callback(
            Long chatId,
            Integer messageId,
            String username,
            String firstName,
            String lastName,
            IBot bot
    );
}
