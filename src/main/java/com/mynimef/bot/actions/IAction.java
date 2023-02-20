package com.mynimef.bot.actions;

import com.mynimef.bot.IBot;

public interface IAction {
    void action(
            String text,
            Long chatId,
            String username,
            String firstName,
            String lastName,
            IBot bot
    );
}
