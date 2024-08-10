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

    private var messageAction: ActionMessage? = null
    private var commandsActions: MutableMap<String, ActionMessage> = HashMap()
    private var contactAction: ActionContact? = null
    private var callbacksActions: MutableMap<String, ActionCallback> = HashMap()

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
                    if (commandsActions.containsKey(command)) {
                        //TODO
                    }
                    commandsActions[command] = action
                }
            } else {
                throw Exception()
            }
        }
        return this
    }

    fun addUpdatesHandler(handler: UpdatesHandler): BotCreator {
        this.messageAction = handler.messageAction
        this.commandsActions.putAll(handler.commandsActions)
        this.contactAction = handler.contactAction
        this.callbacksActions.putAll(handler.callbacksActions)
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
            commandsActions = commandsActions,
            noCommandRecognizedAction = messageAction ?: { message, bot -> },
            contactAction = contactAction ?: { contact, bot -> },
            callbacksActions = callbacksActions,
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
