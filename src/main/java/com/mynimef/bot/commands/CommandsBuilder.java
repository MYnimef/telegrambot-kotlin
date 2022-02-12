package com.mynimef.bot.commands;

import com.mynimef.bot.callback.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CommandsBuilder {
    private final Map<String, ICommand> commands;
    private final Map<Long, ICallback> callbacks;

    public CommandsBuilder() {
        this.commands = new HashMap<>();
        this.callbacks = new HashMap<>();
    }

    public abstract void initialize();

    protected ICustomizationCommand add(String command, String reply) {
        Command com = new Command(reply);
        commands.put(command, com);
        return new CustomizationCommand(com, callbacks);
    }

    protected ICustomizationCommand add(String command, String reply, ICommandAction action) {
        CommandAction com = new CommandAction(reply, action);
        commands.put(command, com);
        return new CustomizationCommand(com, callbacks);
    }

    protected ICustomizationCommand add(String command, String reply, ICommandReply addToReply) {
        CommandReply com = new CommandReply(reply, addToReply);
        commands.put(command, com);
        return new CustomizationCommand(com, callbacks);
    }

    protected ICustomizationCommand add(String command, String reply, ICommandFile addFile) {
        CommandFile com = new CommandFile(reply, addFile);
        commands.put(command, com);
        return new CustomizationCommand(com, callbacks);
    }

    protected void addMultiple(String command, String reply, String baseCommand, ICommandReplyMultiple addToReply) {
        Map<String, String> map_to = new HashMap<>();
        addToReply.action(map_to);

        int i = 1;
        StringBuilder resultReply = new StringBuilder();
        for (Map.Entry<String, String> map: map_to.entrySet()) {
            String com = baseCommand + i;
            add(com, map.getValue());
            resultReply.append("\n").append(map.getKey()).append("\n").append(com).append("\n");
            i++;
        }

        add(command, reply + "\n" + resultReply);
    }

    protected void addMultiple(String command, String reply, String baseCommand, String first, String second, ICommandReplyMultiple addToReply) {
        Map<String, String> map_to = new HashMap<>();
        addToReply.action(map_to);

        int i = 1;
        StringBuilder resultReply = new StringBuilder();
        for (Map.Entry<String, String> map: map_to.entrySet()) {
            String com = baseCommand + i;
            add(com, map.getValue());
            resultReply.append("\n").append(first).append("    ").append(map.getKey()).append("\n").append(second).append("    ").append(com).append("\n");
            i++;
        }

        add(command, reply + "\n" + resultReply);
    }

    public ICustomizationCallback action(ICallbackEdit callback) {
        ICallback cb = new CallbackEdit(callback);
        return new CustomizationCallback(cb);
    }

    public ICustomizationCallback action(ICallbackEditCustom callback) {
        ICallback cb = new CallbackEditCustom(callback);
        return new CustomizationCallback(cb);
    }

    public ICustomizationCallback action(ICallbackSendMessage callback) {
        ICallback cb = new CallbackSendMessage(callback);
        return new CustomizationCallback(cb);
    }

    public Map<String, ICommand> getCommands() { return commands; }

    public Map<Long, ICallback> getCallbacks() { return callbacks; }
}
