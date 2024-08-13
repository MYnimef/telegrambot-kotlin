package com.mynimef.telegrambot.config

import com.mynimef.telegrambot.executable.ActionCallback
import com.mynimef.telegrambot.executable.ActionContact
import com.mynimef.telegrambot.executable.ActionMessage
import okhttp3.internal.toImmutableMap


/**
 * Abstract base class for handling various types of updates in a bot, including messages, commands,
 * and callbacks.
 *
 * This class provides a framework for managing actions associated with different types of updates
 * and allows subclasses to define specific behaviors for handling messages, commands, and callbacks.
 */
abstract class UpdatesHandler {

    /**
     * Abstract property representing the action to be performed when a message is received.
     * Subclasses must provide an implementation for this property.
     */
    abstract val messageAction: ActionMessage

    private val _commandsActions: MutableMap<String, ActionMessage> = HashMap()
    internal val commandsActions by lazy {
        _commandsActions.toImmutableMap()
    }

    /**
     * Adds a command with a static reply action to the command map.
     *
     * This method associates a command with a predefined reply message that will be sent when
     * the command is triggered.
     *
     * @param command The command string to be added to the command map.
     * @param reply The reply message to be sent when the command is triggered.
     */
    protected fun addCommand(command: String, reply: String) {
        _commandsActions[command] = { userCommand, bot ->
            bot.sendMessage(userCommand.chatId, reply)
        }
    }

    /**
     * Adds a command with a custom action to the command map.
     *
     * This method associates a command with a custom `ActionMessage` that will be executed when
     * the command is triggered.
     *
     * @param command The command string to be added to the command map.
     * @param action The `ActionMessage` to be executed when the command is triggered.
     */
    protected fun addCommand(command: String, action: ActionMessage) {
        _commandsActions[command] = action
    }

    /**
     * Abstract property representing the action to be performed when a contact is shared.
     * Subclasses must provide an implementation for this property.
     */
    abstract val contactAction: ActionContact

    private val _callbacksActions: MutableMap<String, ActionCallback> = HashMap()
    internal val callbacksActions by lazy {
        _callbacksActions.toImmutableMap()
    }

    /**
     * Adds a callback ID with a corresponding action to the callback map.
     *
     * This method associates a callback ID with an `ActionCallback` that will be executed when
     * the callback is triggered.
     *
     * @param callbackId The ID of the callback to be added to the callback map.
     * @param action The `ActionCallback` to be executed when the callback is triggered.
     */
    protected fun addCallback(callbackId: String, action: ActionCallback) {
        _callbacksActions[callbackId] = action
    }

}