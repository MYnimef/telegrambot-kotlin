package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.UserUpdate
import com.mynimef.telegrambot.executable.ActionCallback
import com.mynimef.telegrambot.executable.ActionCommand
import com.mynimef.telegrambot.executable.TelegramBot
import com.mynimef.telegrambot.executable.UpdatesHandler
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication
import java.lang.reflect.Method


class BotCreator(
    private val token: String
) {

    fun create(
        updatesHandler: (userUpdate: UserUpdate, bot: IBot) -> Unit
    ) = create(object: UpdatesHandler() {
        override fun onUpdate(userUpdate: UserUpdate, bot: IBot) {
            updatesHandler(userUpdate, bot)
        }
    })

    fun create(updatesHandler: UpdatesHandler): IBot? {
        val bot = TelegramBot(token)

        updatesHandler.bot = bot
        extractFromAnnotations(klass = updatesHandler).let { (commands, callbacks) ->
            updatesHandler.commandsActions = commands
            updatesHandler.callbacksActions = callbacks
        }

        try {
            TelegramBotsLongPollingApplication().use { botsApplication ->
                botsApplication.registerBot(token, updatesHandler.consumer)
                Thread.currentThread().join()
                return bot
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}


private fun extractFromAnnotations(
    klass: Any
): Pair<Map<String, ActionCommand>, Map<String, ActionCallback>> {
    val commandsActions: MutableMap<String, ActionCommand> = HashMap()
    val callbacksActions: MutableMap<String, ActionCallback> = HashMap()
    klass.javaClass.declaredMethods.forEach { method ->
        method.isAccessible = true
        method.annotations.forEach { annotation ->
            when (annotation) {
                is BotCommand -> {
                    commandsActions[annotation.command] = extractCommandAction(klass, method)
                }
                is BotCallback -> {
                    callbacksActions[annotation.callback] = extractCallbackAction(klass, method)
                }
            }
        }
    }
    return commandsActions to callbacksActions
}


@Throws
private fun extractCommandAction(
    klass: Any,
    method: Method
): ActionCommand {
    if(
        method.parameters.size == 2 &&
        method.parameters[0].type == UserUpdate.Command::class.java &&
        method.parameters[1].type == IBot::class.java
    ) {
        val action: ActionCommand = { userCommand, bot ->
            method.invoke(klass, userCommand, bot)
        }
        return action
    } else {
        throw IllegalArgumentException("Method ${method.name} annotated with @BotCommand does not contain needed parameters.")
    }
}


@Throws
private fun extractCallbackAction(
    klass: Any,
    method: Method
): ActionCallback {
    if(
        method.parameters.size == 2 &&
        method.parameters[0].type == UserUpdate.Callback::class.java &&
        method.parameters[1].type == IBot::class.java
    ) {
        val action: ActionCallback = { userCallback, bot ->
            method.invoke(klass, userCallback, bot)
        }
        return action
    } else {
        throw IllegalArgumentException("Method ${method.name} annotated with @BotCallback does not contain needed parameters.")
    }
}
