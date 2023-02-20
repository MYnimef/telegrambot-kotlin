package com.mynimef.bot.config;

import com.mynimef.bot.IBot;
import com.mynimef.bot.actions.IAction;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandsBuilder {

    private final Map<String, IAction> commands;

    public CommandsBuilder() {
        this.commands = new HashMap<>();
    }

    protected abstract void init();

    protected void add(String command, String reply) {
        commands.put(command, (
                String text,
                Long id,
                String username,
                String firstName,
                String lastName,
                IBot bot
        ) -> bot.sendMessage(id, reply));
    }

    protected void add(String command, String reply, IAction action) {
        commands.put(command, (
                String text,
                Long id,
                String username,
                String firstName,
                String lastName,
                IBot bot
        ) -> {
            action.action(text, id, username, firstName, lastName, bot);
            bot.sendMessage(id, reply);
        });
    }

    protected void add(String command, IAction action) { commands.put(command, action); }

    public Map<String, IAction> getCommands() {
        init();
        return commands;
    }

    protected abstract void nonCommandUpdate(
            String text,
            Long id,
            String username,
            String firstName,
            String lastName,
            IBot bot
    );

    public IAction getNoCommandRecognized() {
        return this::nonCommandUpdate;
    }
}