package com.mynimef.bot.executable

import com.mynimef.bot.IBot
import com.mynimef.bot.containers.UserCommand
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message


internal typealias Action = (userCommand: UserCommand, bot: IBot) -> Unit
internal typealias SaveLog = (userCommand: UserCommand) -> Unit


internal class BotConsumer(
    private val telegramBot: TelegramBot,
    private val commands: Map<String, Action>,
    private val noCommandRecognized: Action,
    private val callbacks: Map<String, Action>,
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
        val userCommand = UserCommand(
            text = message.text,
            chatId = message.chatId.toString(),
            messageId = message.messageId,
            username = message.chat.userName,
            firstName = message.chat.firstName,
            lastName = message.chat.lastName
        )

        saveLog?.let { it(userCommand) }
        val action = commands[message.text]
        if (action != null) {
            action(userCommand, telegramBot)
        } else {
            noCommandRecognized(userCommand, telegramBot)
        }
    }

    private fun onCallbackReceived(query: CallbackQuery) {
        val callback = callbacks[query.data]
        callback?.invoke(
            UserCommand(
                    text = "",
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