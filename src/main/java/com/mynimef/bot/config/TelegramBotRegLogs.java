package com.mynimef.bot.config;

import com.mynimef.bot.callback.ICallback;
import com.mynimef.bot.commands.ICommand;
import com.mynimef.bot.registration.IStage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

final class TelegramBotRegLogs<E extends Enum<E>> extends TelegramBotReg<E> {
    private final ISaveLogs saveLog;

    TelegramBotRegLogs(
            Map<String, ICommand> commands,
            Map<Long, ICallback> callbacks,
            Map<E, IStage> stages,
            String token,
            String username,
            IGetState<E> state,
            E commandsState,
            ISaveLogs saveLog
    ) {
        super(
                commands,
                callbacks,
                stages,
                token,
                username,
                state,
                commandsState
        );

        this.saveLog = saveLog;
    }

    @Override
    public void onMessageReceived(Message message) {
        MessageReceiverLogs.onMessageReceived(message, this::handleMessage, saveLog);
    }
}