package com.mynimef.bot.executable

import com.mynimef.bot.IBot
import com.mynimef.bot.actions.ICallback
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
    private val callbacks: Map<String, ICallback>?,
    private val saveLog: SaveLog? = null
): LongPollingSingleThreadUpdateConsumer {

    override fun consume(update: Update) {
        if (update.hasMessage()) {
            val message = update.message

            if (message.hasText()) {
                onMessageReceived(message)
            }
        } else if (update.hasCallbackQuery()) {
            if (callbacks != null) {
                onCallbackReceived(update.callbackQuery)
            } else {
                //TODO
            }
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
        val chatId = query.message.chatId.toString()
        val messageId = query.message.messageId

        val callback = callbacks!![query.data]
        //callback?.callback(chatId, messageId, message, username, firstName, lastName, this)
        //    ?: sendMessage(chatId, "There are no callback \"" + query.data + "\"")
    }

}