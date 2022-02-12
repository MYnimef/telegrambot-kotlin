package com.mynimef.bot.commands;

import com.mynimef.bot.containers.Container;

class Command extends Container<ICommand> implements ICommand {
    private final String reply;

    Command(String reply) {
        this.reply = reply;
    }

    @Override
    public String getReply() { return reply; }
}