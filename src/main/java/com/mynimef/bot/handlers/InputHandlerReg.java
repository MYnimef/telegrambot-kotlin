package com.mynimef.bot.handlers;

import com.mynimef.bot.commands.*;
import com.mynimef.bot.containers.VMFile;
import com.mynimef.bot.registration.IStage;

import java.util.Map;

public final class InputHandlerReg<E extends Enum<E>> extends InputHandler {
    private final IStage stage;
    private final boolean commands;

    public InputHandlerReg(
            String message,
            Long chatId,
            String username,
            String firstName,
            String lastName,
            Map<String, ICommand> commands,
            Map<E, IStage> stages,
            E actualState,
            E commandsState
    ) {
        super(
                message,
                chatId,
                username,
                firstName,
                lastName,
                commands
        );

        this.stage = stages.get(actualState);
        this.commands = (actualState == commandsState);
    }

    @Override
    public InputHandler start() {
        if (commands) {
            commandsHandler();
        } else {
            registrationHandler();
        }
        return this;
    }

    private void registrationHandler() {
        reply.setText(stage.getReply(message, chatIdLong));

        if (stage.doesHaveFiles()) {
            for (VMFile file : stage.getFiles()) {
                addDoc(file, chatIdStr);
            }
        }
    }
}