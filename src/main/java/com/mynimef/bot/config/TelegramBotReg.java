package com.mynimef.bot.config;

import com.mynimef.bot.callback.ICallback;
import com.mynimef.bot.commands.ICommand;
import com.mynimef.bot.handlers.InputHandlerReg;
import com.mynimef.bot.registration.IStage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

class TelegramBotReg<E extends Enum<E>> extends TelegramBot {
    private final Map<E, IStage> stages;
    private final IGetState<E> state;
    private final E commandsState;

    TelegramBotReg(
            Map<String, ICommand> commands,
            Map<Long, ICallback> callbacks,
            Map<E, IStage> stages,
            String token,
            String username,
            IGetState<E> state,
            E commandsState
    ) {
        super(
                commands,
                callbacks,
                token,
                username
        );

        this.stages = stages;
        this.state = state;
        this.commandsState = commandsState;
    }

    @Override
    protected void handleMessage(
            String text,
            Long id,
            String username,
            String firstName,
            String lastName
    ) {
        new Thread(
                () -> getHandler(
                        new InputHandlerReg<>(
                                text,
                                id,
                                username,
                                firstName,
                                lastName,
                                commands,
                                stages,
                                state.getState(id),
                                commandsState
                        )
                                .start()
                )
        )
                .start();
    }

    @Override
    public void onMessageReceived(Message message) {
        MessageReceiverNoLogs.onMessageReceived(message, this::handleMessage);
    }
}
