package com.mynimef.bot.commands;

final class CommandReply extends Command implements ICommandReply {
    private final ICommandReply action;

    public CommandReply(String reply, ICommandReply action) {
        super(reply);
        this.action = action;
    }

    @Override
    public String action(Long chatId, String username, String firstName, String lastName, String replyConst) {
        return action.action(chatId, username, firstName, lastName, replyConst);
    }
}