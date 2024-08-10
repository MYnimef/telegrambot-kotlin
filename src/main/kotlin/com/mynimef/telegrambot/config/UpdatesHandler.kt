package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.executable.ActionCallback
import com.mynimef.telegrambot.executable.ActionContact
import com.mynimef.telegrambot.executable.ActionMessage
import okhttp3.internal.toImmutableMap


abstract class UpdatesHandler {

    abstract val messageAction: ActionMessage

    private val _commandsActions: MutableMap<String, ActionMessage> = HashMap()
    internal val commandsActions by lazy {
        _commandsActions.toImmutableMap()
    }

    protected fun addCommand(command: String, reply: String) {
        _commandsActions[command] = { userCommand, bot ->
            bot.sendMessage(userCommand.userId, reply)
        }
    }

    protected fun addCommand(command: String, action: ActionMessage) {
        _commandsActions[command] = action
    }

    abstract val contactAction: ActionContact

    private val _callbacksActions: MutableMap<String, ActionCallback> = HashMap()
    internal val callbacksActions by lazy {
        _callbacksActions.toImmutableMap()
    }

    protected fun addCallback(callbackId: String, action: ActionCallback) {
        _callbacksActions[callbackId] = action
    }

}