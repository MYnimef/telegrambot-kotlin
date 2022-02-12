package com.mynimef.bot.commands;

public interface ICommandReply {
    String action(Long chatId, String username, String firstName, String lastName, String replyConst);
}