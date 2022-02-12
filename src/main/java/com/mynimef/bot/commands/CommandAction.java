package com.mynimef.bot.commands;

final class CommandAction extends Command implements ICommandAction {
    private final ICommandAction action;

    CommandAction(String reply, ICommandAction action) {
        super(reply);
        this.action = action;
    }

    @Override
    public void action(Long chatId, String username, String firstName, String lastName) {
        action.action(chatId, username, firstName, lastName);
    }
}
