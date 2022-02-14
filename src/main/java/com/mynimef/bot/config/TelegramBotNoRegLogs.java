package com.mynimef.bot.config;

import com.mynimef.bot.callback.ICallback;
import com.mynimef.bot.commands.ICommand;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

final class TelegramBotNoRegLogs extends TelegramBotNoReg {
    private final ISaveLogs saveLog;

    TelegramBotNoRegLogs(
            Map<String, ICommand> commands,
            ICommand noCommandRecognized,
            Map<Long, ICallback> callbacks,
            String token,
            String username,
            ISaveLogs saveLog
    ) {
        super(
                commands,
                noCommandRecognized,
                callbacks,
                token,
                username
        );

        this.saveLog = saveLog;
    }

    @Override
    public void onMessageReceived(Message message) {
        MessageReceiverLogs.onMessageReceived(message, this::handleMessage, saveLog);
    }
}