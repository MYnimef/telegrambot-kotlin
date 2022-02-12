package com.mynimef.bot.commands;

final class CommandFile extends Command implements ICommandFile {
    private final ICommandFile action;

    CommandFile(String reply, ICommandFile action) {
        super(reply);
        this.action = action;
    }

    @Override
    public boolean action(Long chatId, String username, String firstName, String lastName) {
        return action.action(chatId, username, firstName, lastName);
    }
}
