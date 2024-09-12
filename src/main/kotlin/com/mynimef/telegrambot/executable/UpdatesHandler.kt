package com.mynimef.telegrambot.executable

import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.UserUpdate
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.message.Message


internal typealias ActionCommand = (UserUpdate.Command, IBot) -> Unit
internal typealias ActionCallback = (UserUpdate.Callback, IBot) -> Unit


abstract class UpdatesHandler {

    internal lateinit var bot: IBot

    internal lateinit var commandsActions: Map<String, ActionCommand>
    internal lateinit var callbacksActions: Map<String, ActionCallback>

    internal val consumer = LongPollingSingleThreadUpdateConsumer { update ->
        if (update.hasMessage()) {
            val message = update.message
            if (message.hasText()) {
                onMessageReceived(message)
            } else if (message.hasContact()) {
                onContactReceived(message)
            }
        } else if (update.hasCallbackQuery()) {
            onCallbackReceived(update.callbackQuery)
        }
    }

    private fun onMessageReceived(message: Message) {
        if (message.isCommand) {
            val userCommand = UserUpdate.Command(
                command = message.text,
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                username = message.chat.userName,
                firstName = message.chat.firstName,
                lastName = message.chat.lastName
            )
            onUpdate(userUpdate = userCommand, bot = bot)
        } else {
            val userMessage = UserUpdate.Message(
                text = message.text,
                chatId = message.chatId.toString(),
                messageId = message.messageId,
                username = message.chat.userName,
                firstName = message.chat.firstName,
                lastName = message.chat.lastName
            )
            onUpdate(userUpdate = userMessage, bot = bot)
        }
    }

    private fun onContactReceived(message: Message) {
        val contact = message.contact
        val contactUpdate = UserUpdate.Contact(
            contactInfo = UserUpdate.Contact.ContactInfo(
                phoneNumber = contact.phoneNumber,
                firstName = contact.firstName,
                lastName = contact.lastName,
                userId = contact.userId?.toString()
            ),
            chatId = message.chatId.toString(),
            username = message.chat.userName,
            firstName = message.chat.firstName,
            lastName = message.chat.lastName
        )
        onUpdate(userUpdate = contactUpdate, bot = bot)
    }

    private fun onCallbackReceived(query: CallbackQuery) {
        if (query.data != null) {
            val callbackUpdate = UserUpdate.Callback(
                callbackId = query.data,
                chatId = query.message.chatId.toString(),
                originalMessageId = query.message.messageId,
                username = query.message.chat.userName,
                firstName = query.message.chat.firstName,
                lastName = query.message.chat.lastName
            )
            onUpdate(userUpdate = callbackUpdate, bot = bot)
        }
    }

    protected abstract fun onUpdate(userUpdate: UserUpdate, bot: IBot)

    protected fun processCommand(update: UserUpdate.Command) {
        commandsActions[update.command]?.invoke(update, bot)
    }

    protected fun processCallback(update: UserUpdate.Callback) {
        callbacksActions[update.callbackId]?.invoke(update, bot)
    }

 }