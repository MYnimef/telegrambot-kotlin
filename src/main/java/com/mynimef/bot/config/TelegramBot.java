package com.mynimef.bot.config;

import com.mynimef.bot.callback.ICallback;
import com.mynimef.bot.commands.ICommand;
import com.mynimef.bot.handlers.CallbackHandler;
import com.mynimef.bot.handlers.IHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;

abstract class TelegramBot extends TelegramLongPollingBot implements IBot, IMessageReceiver {
    protected final Map<String, ICommand> commands;
    private final Map<Long, ICallback> callbacks;

    private final String token;
    private final String username;

    TelegramBot(
            Map<String, ICommand> commands,
            Map<Long, ICallback> callbacks,
            String token,
            String username
    ) {
        this.commands = commands;
        this.callbacks = callbacks;
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotUsername() { return username; }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.hasText()) {
                onMessageReceived(message);
            }
        } else if (update.hasCallbackQuery()) {
            new Thread(
                    () -> getHandler(
                            new CallbackHandler(
                                    update.getCallbackQuery().getMessage().getChatId().toString(),
                                    update.getCallbackQuery().getMessage().getMessageId(),
                                    update.getCallbackQuery().getData(),
                                    callbacks
                            )
                                    .start()
                    )
            )
                    .start();
        }
    }

    protected abstract void handleMessage(
            String text,
            Long id,
            String username,
            String firstName,
            String lastName
    );

    protected void getHandler(IHandler handler) {
        sendMessage(handler.getReply());
        sendDocs(handler.getDocs());
    }

    private void sendMessage(BotApiMethod<?> reply) {
        try {
            execute(reply);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendDocs(List<SendDocument> docs) {
        try {
            for (SendDocument doc : docs) {
                execute(doc);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        sendMessage(message);
    }
}
