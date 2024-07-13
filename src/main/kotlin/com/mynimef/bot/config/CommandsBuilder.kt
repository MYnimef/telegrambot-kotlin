package com.mynimef.bot.config

import com.mynimef.bot.IBot
import com.mynimef.bot.containers.UserCommand
import com.mynimef.bot.executable.Action


abstract class CommandsBuilder {

    private val _commands: MutableMap<String, Action> = HashMap()
    internal val commands: Map<String, (command: UserCommand, bot: IBot) -> Unit> by lazy {
        init()
        _commands
    }

    protected abstract fun init()

    protected fun add(command: String, reply: String) {
        _commands[command] = { userCommand, bot ->
            bot.sendMessage(userCommand.chatId, reply)
        }
    }

    protected fun add(command: String, action: Action) {
        _commands[command] = action
    }

    abstract val nonCommandUpdate: Action

}