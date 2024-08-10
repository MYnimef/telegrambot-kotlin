package com.mynimef.telegrambot.executable

import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.UserCallback
import com.mynimef.telegrambot.containers.UserContact
import com.mynimef.telegrambot.containers.UserMessage
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message


typealias ActionMessage = (userMessage: UserMessage, bot: IBot) -> Unit
typealias ActionContact = (userContact: UserContact, bot: IBot) -> Unit
typealias ActionCallback = (userCallback: UserCallback, bot: IBot) -> Unit
typealias SaveLog = (userMessage: UserMessage) -> Unit


internal class BotConsumer(
    private val telegramBot: TelegramBot,
    private val commandsActions: Map<String, ActionMessage>,
    private val noCommandRecognizedAction: ActionMessage,
    private val contactAction: ActionContact,
    private val callbacksActions: Map<String, ActionCallback>,
    private val saveLog: SaveLog? = null
): LongPollingSingleThreadUpdateConsumer {

    override fun consume(update: Update) {
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
        val userMessage = UserMessage(
            text = message.text,
            userId = message.chatId.toString(),
            messageId = message.messageId,
            username = message.chat.userName,
            firstName = message.chat.firstName,
            lastName = message.chat.lastName
        )

        saveLog?.invoke(userMessage)
        commandsActions[message.text]?.invoke(userMessage, telegramBot) ?: noCommandRecognizedAction(userMessage, telegramBot)
    }

    private fun onContactReceived(message: Message) {
        val contact = message.contact
        val userContact = UserContact(
            contactInfo = UserContact.ContactInfo(
                phoneNumber = contact.phoneNumber,
                firstName = contact.firstName,
                lastName = contact.lastName,
                userId = contact.userId?.toString()
            ),
            userId = message.chatId.toString(),
            username = message.chat.userName,
            firstName = message.chat.firstName,
            lastName = message.chat.lastName
        )
        contactAction(userContact, telegramBot)
    }

    private fun onCallbackReceived(query: CallbackQuery) {
        val callback = callbacksActions[query.data]
        callback?.invoke(
            UserCallback(
                callbackId = query.data,
                userId = query.message.chatId.toString(),
                originalMessageId = query.message.messageId,
                username = query.message.chat.userName,
                firstName = query.message.chat.firstName,
                lastName = query.message.chat.lastName
            ),
            telegramBot
        )
    }

}