package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.executable.ActionMessage
import okhttp3.internal.toImmutableMap


abstract class CommandsBuilder {

    private val _commands: MutableMap<String, ActionMessage> = HashMap()
    internal val commands by lazy {
        init()
        _commands.toImmutableMap()
    }

    protected abstract fun init()

    protected fun add(command: String, reply: String) {
        _commands[command] = { userCommand, bot ->
            bot.sendMessage(userCommand.chatId, reply)
        }
    }

    protected fun add(command: String, action: ActionMessage) {
        _commands[command] = action
    }

    abstract val nonCommandUpdate: ActionMessage

}