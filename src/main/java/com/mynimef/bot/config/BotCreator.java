package com.mynimef.bot.config;

import com.mynimef.bot.IBot;
import com.mynimef.bot.actions.ISaveLogs;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public final class BotCreator {

    private final String token;
    private final String username;

    private CommandsBuilder commands;
    private CallbacksBuilder callbacks;

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
        return this;
    }

    public BotCreator addCallbacks(CallbacksBuilder callbacks) {
        this.callbacks = callbacks;
        return this;
    }

    public BotCreator addLogs(ISaveLogs logs) {
        this.logs = logs;
        return this;
    }

    public IBot start() {
        if (commands == null) {
            commands = getEmptyCommands();
        }

        return initBot(
                new TelegramBot(
                        commands.getCommands(),
                        commands.getNoCommandRecognized(),
                        (callbacks != null) ? callbacks.getCallbacks() : null,
                        token,
                        username,
                        logs
                )
        );
    }

    private TelegramBot initBot(TelegramBot bot) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);

            try {
                telegramBotsApi.registerBot(bot);
                return bot;
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    private CommandsBuilder getEmptyCommands() {
        return new CommandsBuilder() {
            @Override
            public void init() { }

            @Override
            protected void nonCommandUpdate(
                    String text,
                    String chatId,
                    Integer messageId,
                    String username,
                    String firstName,
                    String lastName,
                    IBot bot
            ) {
                bot.sendMessage(chatId, "Configure CommandsBuilder class");
            }
        };
    }
}
