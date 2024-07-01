package com.mynimef.bot.config

import com.mynimef.bot.IBot
import com.mynimef.bot.actions.ICallback
import com.mynimef.bot.containers.*
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.io.File
import java.io.Serializable

internal typealias Action = (userCommand: UserCommand, bot: IBot) -> Unit
internal typealias SaveLog = (userCommand: UserCommand) -> Unit

internal class TelegramBot(
    private val commands: Map<String, Action>,
    private val noCommandRecognized: Action,
    private val callbacks: Map<String, ICallback>?,
    token: String?,
    private val username: String,
    private val saveLog: SaveLog? = null
) : TelegramLongPollingBot(token), IBot {

    override fun getBotUsername(): String {
        return username
    }

    override fun onUpdateReceived(update: Update) = Thread {
        if (update.hasMessage()) {
            val message = update.message

            if (message.hasText()) {
                onMessageReceived(message)
            }
        } else if (update.hasCallbackQuery()) {
            if (callbacks != null) {
                onCallbackReceived(update.callbackQuery)
            } else {
                sendMessage(
                    update.callbackQuery.message.chatId.toString(),
                    "There are no callbacks. Please, set them properly"
                )
            }
        }
    }.start()

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
            action(userCommand, this)
        } else {
            noCommandRecognized(userCommand, this)
        }
    }

    private fun<T: Serializable> sendMessage(reply: BotApiMethod<T>): Int? {
        try {
            val sentMessage = execute(reply) as Message
            return sentMessage.messageId
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return null
    }

    private fun sendDoc(doc: SendDocument) {
        try {
            execute(doc)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    override fun sendMessage(chatId: String, message: BotMessage): Int? {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId
        sendMessage.text = message.text

        if (message.doesHaveButtons()) {
            sendMessage.replyMarkup = setReply(message.buttons)
        }

        val messageId = sendMessage(sendMessage)
        for (file in message.files) {
            sendDoc(chatId, file)
        }
        return messageId
    }

    override fun sendDoc(chatId: String, file: BotFile) {
        val doc = SendDocument()
        doc.chatId = chatId
        doc.document = InputFile(File(file.path()))
        doc.caption = file.description()
        sendDoc(doc)
    }

    override fun sendMessage(chatId: String, text: String): Int? {
        val message = SendMessage()
        message.chatId = chatId
        message.text = text
        return sendMessage(message)
    }

    private fun onCallbackReceived(query: CallbackQuery) {
        val chatId = query.message.chatId.toString()
        val messageId = query.message.messageId

        val callback = callbacks!![query.data]
        //callback?.callback(chatId, messageId, message, username, firstName, lastName, this)
        //    ?: sendMessage(chatId, "There are no callback \"" + query.data + "\"")
    }

    override fun editMessage(chatId: String, messageId: Int, text: String) {
        val editMessage = EditMessageText()
        editMessage.chatId = chatId
        editMessage.messageId = messageId
        editMessage.text = text
        sendMessage(editMessage)
    }

    override fun editMessage(chatId: String, messageId: Int, message: BotMessage) {
        val editMessage = EditMessageText()

        editMessage.chatId = chatId
        editMessage.messageId = messageId
        editMessage.text = message.text

        if (message.doesHaveButtons()) {
            editMessage.replyMarkup = setReply(message.buttons)
        }

        sendMessage(editMessage)
        for (file in message.files) {
            sendDoc(chatId, file)
        }
    }

    override fun deleteMessage(chatId: String, messageId: Int) {
        val delete = DeleteMessage()
        delete.chatId = chatId
        delete.messageId = messageId

        try {
            execute(delete)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

}


private fun setReply(buttons: List<ButtonLine>): InlineKeyboardMarkup {
    val replyMarkup = InlineKeyboardMarkup()
    val keyboardButtons: MutableList<List<InlineKeyboardButton>> = ArrayList()

    for (line in buttons) {
        val keyboardButtonsRow: MutableList<InlineKeyboardButton> = ArrayList()
        for ((label, callback) in line.line) {
            val inlineKeyboardButton = InlineKeyboardButton()
            inlineKeyboardButton.text = label
            inlineKeyboardButton.callbackData = callback
            keyboardButtonsRow.add(inlineKeyboardButton)
        }
        keyboardButtons.add(keyboardButtonsRow)
    }

    replyMarkup.keyboard = keyboardButtons
    return replyMarkup
}


private fun setKeyboard(buttons: Array<ButtonKeyboardLine>): ReplyKeyboardMarkup {
    val replyMarkup = ReplyKeyboardMarkup()
    val keyboardButtons: MutableList<KeyboardRow> = ArrayList()

    for (line in buttons) {
        val keyboardRow = KeyboardRow()
        for (text in line.line) {
            keyboardRow.add(text)
            keyboardButtons.add(keyboardRow)
        }
    }

    replyMarkup.keyboard = keyboardButtons
    replyMarkup.resizeKeyboard = true
    replyMarkup.oneTimeKeyboard = true
    return replyMarkup
}
