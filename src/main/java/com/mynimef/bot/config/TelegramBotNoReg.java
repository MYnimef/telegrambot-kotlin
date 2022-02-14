package com.mynimef.bot.config;

import com.mynimef.bot.callback.ICallback;
import com.mynimef.bot.commands.ICommand;
import com.mynimef.bot.handlers.InputHandler;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Map;

class TelegramBotNoReg extends TelegramBot {
    TelegramBotNoReg(
            Map<String, ICommand> commands,
            ICommand noCommandRecognized,
            Map<Long, ICallback> callbacks,
            String token,
            String username
    ) {
        super(
                commands,
                noCommandRecognized,
                callbacks,
                token,
                username
        );
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
                        new InputHandler(
                                text,
                                id,
                                username,
                                firstName,
                                lastName,
                                commands,
                                noCommandRecognized
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
