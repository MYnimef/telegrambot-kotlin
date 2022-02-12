package com.mynimef.bot.commands;

import com.mynimef.bot.callback.ICallback;

import java.util.HashMap;
import java.util.Map;

public abstract class CommandsBuilder {
    private final Map<String, ICommand> commands;
    private final Map<Long, ICallback> callbacks;

    public CommandsBuilder() {
        this.commands = new HashMap<>();
        this.callbacks = new HashMap<>();
    }

    public abstract void initialize();

    protected ICustomization add(String command, String reply) {
        Command com = new Command(reply);
        commands.put(command, com);
        return new Customization(com, callbacks);
    }

    protected ICustomization add(String command, String reply, ICommandAction action) {
        CommandAction com = new CommandAction(reply, action);
        commands.put(command, com);
        return new Customization(com, callbacks);
    }

    protected ICustomization add(String command, String reply, ICommandReply addToReply) {
        CommandReply com = new CommandReply(reply, addToReply);
        commands.put(command, com);
        return new Customization(com, callbacks);
    }

    protected ICustomization add(String command, String reply, ICommandFile addFile) {
        CommandFile com = new CommandFile(reply, addFile);
        commands.put(command, com);
        return new Customization(com, callbacks);
    }

    public Map<String, ICommand> getCommands() { return commands; }

    public Map<Long, ICallback> getCallbacks() { return callbacks; }
}
