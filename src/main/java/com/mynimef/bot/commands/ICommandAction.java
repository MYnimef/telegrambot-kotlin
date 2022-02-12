package com.mynimef.bot.commands;

public interface ICommandAction {
    void action(Long chatId, String username, String firstName, String lastName);
}
