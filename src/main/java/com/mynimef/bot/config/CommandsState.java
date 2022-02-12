package com.mynimef.bot.config;

class CommandsState<E extends Enum<E>> {
    private final E commandsState;

    CommandsState(E commandsState) {
        this.commandsState = commandsState;
    }

    E getCommandsState() {
        return commandsState;
    }
}
