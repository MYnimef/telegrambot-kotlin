package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.UserUpdate
import com.mynimef.telegrambot.executable.*
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication


class BotCreator(

    private val token: String

) {

    private var messageAction: ActionMessage? = null
    private var commandsActions: MutableMap<String, ActionMessage> = HashMap()
    private var contactAction: ActionContact? = null
    private var callbacksActions: MutableMap<String, ActionCallback> = HashMap()

    @Throws(IllegalArgumentException::class)
    fun addCommands(commandsClass: Any): BotCreator {
        commandsClass.javaClass.declaredMethods.forEach { method ->
            val annotation = method.annotations.find { it is BotCommand } as? BotCommand ?: return@forEach
            if (
                method.parameters.size == 2 &&
                method.parameters[0].type == UserUpdate.Message::class.java &&
                method.parameters[1].type == IBot::class.java
            ) {
                val action: ActionMessage = { userCommand, bot ->
                    method.invoke(commandsClass, userCommand, bot)
                }
                annotation.commands.forEach { command ->
                    if (commandsActions.containsKey(command)) {
                        throw IllegalArgumentException("Duplicate command ${commandsActions[command]}")
                    }
                    commandsActions[command] = action
                }
            } else {
                throw IllegalArgumentException("Method ${method.name} annotated with @BotCommand does not contain needed parameters.")
            }
        }
        return this
    }

    @Throws(IllegalArgumentException::class)
    fun addCallbacks(callbacksClass: Any): BotCreator {
        callbacksClass.javaClass.declaredMethods.forEach { method ->
            val annotation = method.annotations.find { it is BotCallback } as? BotCallback ?: return@forEach
            if (
                method.parameters.size == 2 &&
                method.parameters[0].type == UserUpdate.Callback::class.java &&
                method.parameters[1].type == IBot::class.java
            ) {
                if (callbacksActions.containsKey(annotation.callback)) {
                    throw IllegalArgumentException("Duplicate callback ${annotation.callback}")
                }
                val action: ActionCallback = { userCallback, bot ->
                    method.invoke(callbacksClass, userCallback, bot)
                }
                callbacksActions[annotation.callback] = action
            } else {
                throw IllegalArgumentException("Method ${method.name} annotated with @BotCallback does not contain needed parameters.")
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

    fun start(): IBot? {
        val bot = TelegramBot(token)
        val botConsumer = BotConsumer(
            telegramBot = bot,
            commandsActions = commandsActions,
            messageAction = messageAction ?: { message, bot -> },
            contactAction = contactAction ?: { contact, bot -> },
            callbacksActions = callbacksActions,
        )

        try {
            TelegramBotsLongPollingApplication().use { botsApplication ->
                botsApplication.registerBot(token, botConsumer)
                Thread.currentThread().join()
                return bot
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}
