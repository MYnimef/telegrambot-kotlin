package com.mynimef.bot.config

import com.mynimef.bot.IBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

class BotCreator(
    private val token: String,
    private val username: String
) {

    private var commands: CommandsBuilder? = null
    private var callbacks: CallbacksBuilder? = null

    private var logs: SaveLog? = null

    fun addCommands(commands: CommandsBuilder?): BotCreator {
        this.commands = commands
        return this
    }

    fun addCallbacks(callbacks: CallbacksBuilder?): BotCreator {
        this.callbacks = callbacks
        return this
    }

    fun addLogs(logs: SaveLog): BotCreator {
        this.logs = logs
        return this
    }

    fun start(): IBot? {
        if (commands == null) {
            commands = object : CommandsBuilder() {
                public override fun init() {}
                override val nonCommandUpdate: Action
                    get() = { it, bot ->
                        bot.sendMessage(it.chatId, "Configure CommandsBuilder class")
                    }
            }
        }

        return initBot(
            TelegramBot(
                commands = commands!!.commands,
                noCommandRecognized = commands!!.nonCommandUpdate,
                callbacks = if ((callbacks != null)) callbacks!!.callbacks else null,
                token = token,
                username = username,
                saveLog = logs
            )
        )
    }

    private fun initBot(bot: TelegramBot): TelegramBot? {
        try {
            val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

            try {
                telegramBotsApi.registerBot(bot)
                return bot
            } catch (e: TelegramApiRequestException) {
                e.printStackTrace()
            }
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }

        return null
    }

}
