package com.mynimef.bot.executable

import com.mynimef.bot.IBot
import com.mynimef.bot.containers.UserCallback
import com.mynimef.bot.containers.UserMessage
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message


internal typealias ActionMessage = (userMessage: UserMessage, bot: IBot) -> Unit
internal typealias ActionCallback = (userCallback: UserCallback, bot: IBot) -> Unit
internal typealias SaveLog = (userMessage: UserMessage) -> Unit


internal class BotConsumer(
    private val telegramBot: TelegramBot,
    private val commands: Map<String, ActionMessage>,
    private val noCommandRecognized: ActionMessage,
    private val callbacks: Map<String, ActionCallback>,
    private val saveLog: SaveLog? = null
): LongPollingSingleThreadUpdateConsumer {

    override fun consume(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            if (message.hasText()) {
                onMessageReceived(message)
            }
        } else if (update.hasCallbackQuery()) {
            onCallbackReceived(update.callbackQuery)
        }
    }

    private fun onMessageReceived(message: Message) {
        val userMessage = UserMessage(
            text = message.text,
            chatId = message.chatId.toString(),
            messageId = message.messageId,
            username = message.chat.userName,
            firstName = message.chat.firstName,
            lastName = message.chat.lastName
        )

        saveLog?.let { it(userMessage) }
        val action = commands[message.text]
        if (action != null) {
            action(userMessage, telegramBot)
        } else {
            noCommandRecognized(userMessage, telegramBot)
        }
    }

    private fun onCallbackReceived(query: CallbackQuery) {
        val callback = callbacks[query.data]
        callback?.invoke(
            UserCallback(
                    callbackId = query.data,
                    chatId = query.message.chatId.toString(),
                    messageId = query.message.messageId,
                    username = query.message.chat.userName,
                    firstName = query.message.chat.firstName,
                    lastName = query.message.chat.lastName
            ),
            telegramBot
        )
    }

}