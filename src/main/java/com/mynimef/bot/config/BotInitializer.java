package com.mynimef.bot.config;

import com.mynimef.bot.commands.CommandsBuilder;
import com.mynimef.bot.registration.RegistrationBuilder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

class BotInitializer<E extends Enum<E>> {
    private final TelegramBot bot;

    BotInitializer(
            CommandsBuilder commands,
            RegistrationBuilder<E> stages,
            String token,
            String username,
            IGetState<E> state,
            E commandsState
    ) {
        this.bot = new TelegramBotReg<>(
                commands.getCommands(),
                commands.getCallbacks(),
                stages.getStages(),
                token,
                username,
                state,
                commandsState
        );
    }

    BotInitializer(
            CommandsBuilder commands,
            RegistrationBuilder<E> stages,
            String token,
            String username,
            IGetState<E> state,
            E commandsState,
            ISaveLogs logs
    ) {
        this.bot = new TelegramBotRegLogs<>(
                commands.getCommands(),
                commands.getCallbacks(),
                stages.getStages(),
                token,
                username,
                state,
                commandsState,
                logs
        );
    }

    BotInitializer(
            CommandsBuilder commands,
            String token,
            String username
    ) {
        this.bot = new TelegramBotNoReg(
                commands.getCommands(),
                commands.getCallbacks(),
                token,
                username
        );
    }

    BotInitializer(
            CommandsBuilder commands,
            String token,
            String username,
            ISaveLogs logs
    ) {
        this.bot = new TelegramBotNoRegLogs(
                commands.getCommands(),
                commands.getCallbacks(),
                token,
                username,
                logs
        );
    }

    IBot Init() {
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
}
