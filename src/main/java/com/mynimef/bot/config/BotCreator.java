package com.mynimef.bot.config;

import com.mynimef.bot.commands.CommandsBuilder;
import com.mynimef.bot.registration.RegistrationBuilder;

@SuppressWarnings("rawtypes")
public class BotCreator {
    private final String token;
    private final String username;

    private CommandsBuilder commands;

    private RegistrationBuilder stages;
    private IGetState state;
    private CommandsState commandsState;

    private ISaveLogs logs;

    public BotCreator(
            String token,
            String username
    ) {
        this.token = token;
        this.username = username;
    }

    public BotCreator addCommands(CommandsBuilder commands) {
        this.commands = commands;
        this.commands.initialize();
        return this;
    }

    public <E extends Enum<E>> BotCreator addRegistration(
            RegistrationBuilder<E> stages,
            IGetState<E> state,
            E commandsState
    ) {
        this.stages = stages;
        this.state = state;
        this.commandsState = new CommandsState<>(commandsState);
        return this;
    }

    public BotCreator addLogs(ISaveLogs logs) {
        this.logs = logs;
        return this;
    }

    @SuppressWarnings("unchecked")
    public IBot start() {
        if (stages != null && state != null && commandsState != null) {
            if (logs != null) {
                return new BotInitializer(
                        commands != null ? commands : new CommandsBuilder() {
                            @Override
                            public void initialize() { }
                        },
                        stages,
                        token,
                        username,
                        state,
                        commandsState.getCommandsState(),
                        logs
                )
                        .Init();
            } else {
                return new BotInitializer(
                        commands != null ? commands : new CommandsBuilder() {
                            @Override
                            public void initialize() { }
                        },
                        stages,
                        token,
                        username,
                        state,
                        commandsState.getCommandsState()
                )
                        .Init();
            }
        } else {
            if (logs != null) {
                return new BotInitializer(
                        commands != null ? commands : new CommandsBuilder() {
                            @Override
                            public void initialize() { }
                        },
                        token,
                        username,
                        logs
                )
                        .Init();
            } else {
                return new BotInitializer(
                        commands != null ? commands : new CommandsBuilder() {
                            @Override
                            public void initialize() { }
                        },
                        token,
                        username
                )
                        .Init();
            }
        }
    }
}
