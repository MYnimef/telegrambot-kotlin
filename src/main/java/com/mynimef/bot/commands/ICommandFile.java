package com.mynimef.bot.commands;

public interface ICommandFile {
    boolean action(Long chatId, String username, String firstName, String lastName);
}
