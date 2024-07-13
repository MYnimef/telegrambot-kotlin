package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.BotCommand
import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.UserMessage
import com.mynimef.telegrambot.executable.*
import com.mynimef.telegrambot.executable.ActionMessage
import com.mynimef.telegrambot.executable.BotConsumer
import com.mynimef.telegrambot.executable.SaveLog
import com.mynimef.telegrambot.executable.TelegramBot
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication


class BotCreator(

    private val token: String

) {

    private var commands: MutableMap<String, ActionMessage> = HashMap()
    private var callbacks: MutableMap<String, ActionCallback> = HashMap()

    private var logs: SaveLog? = null

    fun addCommands(commandsClass: Any): BotCreator {
        // Iterate through all declared functions in the class
        commandsClass.javaClass.declaredMethods.forEach { method ->
            val annotation = method.annotations.find { it is BotCommand } as BotCommand?
            if (
                annotation != null &&
                method.parameters.size == 2 &&
                method.parameters[0].type == UserMessage::class.java &&
                method.parameters[1].type == IBot::class.java &&
                method.returnType == Void.TYPE
            ) {
                val action: ActionMessage = { userCommand, bot ->
                    method.invoke(commandsClass, userCommand, bot)
                }
                annotation.commands.forEach { command ->
                    if (commands.containsKey(command)) {
                        //TODO
                    }
                    commands[command] = action
                }
            } else {
                throw Exception()
            }
        }
        return this
    }

    fun addCommands(builder: CommandsBuilder): BotCreator {
        this.commands.putAll(builder.commands)
        return this
    }

    fun addCallbacks(builder: CallbacksBuilder): BotCreator {
        this.callbacks.putAll(builder.callbacks)
        return this
    }

    fun addLogs(logs: SaveLog): BotCreator {
        this.logs = logs
        return this
    }

    fun start(): IBot? {
        val bot = TelegramBot(token)
        val botConsumer = BotConsumer(
            telegramBot = bot,
            commands = commands,
            noCommandRecognized = { ii, hh -> },
            callbacks = callbacks,
            saveLog = logs
        )

        try {
            TelegramBotsLongPollingApplication().use { botsApplication ->
                botsApplication.registerBot(token, botConsumer)
                Thread.currentThread().join()
                return bot
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

}
