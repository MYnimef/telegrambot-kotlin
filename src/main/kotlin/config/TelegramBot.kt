package config

import IBot
import actions.ICallback
import com.mynimef.bot.containers.*
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.io.File
import java.io.Serializable


internal typealias Action = (userCommand: UserCommand, bot: IBot) -> Unit
internal typealias SaveLog = (userCommand: UserCommand) -> Unit

internal class TelegramBot(
    private val commands: Map<String, Action>,
    private val noCommandRecognized: Action,
    private val callbacks: Map<String, ICallback>?,
    token: String,
    private val saveLog: SaveLog? = null
) : LongPollingSingleThreadUpdateConsumer, IBot {

    private val telegramClient: TelegramClient = OkHttpTelegramClient(token)

    override fun consume(update: Update) = Thread {
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
            val sentMessage = telegramClient.execute(reply) as Message
            return sentMessage.messageId
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return null
    }

    private fun sendDoc(doc: SendDocument) {
        try {
            telegramClient.execute(doc)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    override fun sendMessage(chatId: String, message: BotMessage): Int? {
        val sendMessage = SendMessage(
            chatId,
            message.text
        )

        if (message.doesHaveButtons()) {
            sendMessage.replyMarkup = setReply(message.buttons)
        }

        val messageId = sendMessage(sendMessage)
        for (file in message.files) {
            sendDoc(chatId, file)
        }
        return messageId
    }

    override fun sendDoc(chatId: String, botFile: BotFile) {
        val doc = SendDocument(chatId, InputFile(botFile.file))
        doc.caption = botFile.description
        sendDoc(doc)
    }

    override fun sendMessage(chatId: String, text: String): Int? {
        val message = SendMessage(
            chatId,
            text
        )
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
        val editMessage = EditMessageText(text)
        editMessage.chatId = chatId
        editMessage.messageId = messageId
        sendMessage(editMessage)
    }

    override fun editMessage(chatId: String, messageId: Int, message: BotMessage) {
        val editMessage = EditMessageText(message.text)

        editMessage.chatId = chatId
        editMessage.messageId = messageId

        if (message.doesHaveButtons()) {
            editMessage.replyMarkup = setReply(message.buttons)
        }

        sendMessage(editMessage)
        for (file in message.files) {
            sendDoc(chatId, file)
        }
    }

    override fun deleteMessage(chatId: String, messageId: Int) {
        val delete = DeleteMessage(chatId, messageId)
        try {
            telegramClient.execute(delete)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

}


private fun setReply(buttons: List<ButtonLine>): InlineKeyboardMarkup {
    val keyboardButtons: MutableList<InlineKeyboardRow> = ArrayList()
    for (line in buttons) {
        val keyboardButtonsRow = InlineKeyboardRow()
        for ((label, callback) in line.line) {
            val inlineKeyboardButton = InlineKeyboardButton(label)
            inlineKeyboardButton.callbackData = callback
            keyboardButtonsRow.add(inlineKeyboardButton)
        }
        keyboardButtons.add(keyboardButtonsRow)
    }
    return InlineKeyboardMarkup(keyboardButtons)
}


private fun setKeyboard(buttons: Array<ButtonKeyboardLine>): ReplyKeyboardMarkup {
    val keyboardButtons: MutableList<KeyboardRow> = ArrayList()

    for (line in buttons) {
        val keyboardRow = KeyboardRow()
        for (text in line.line) {
            keyboardRow.add(text)
            keyboardButtons.add(keyboardRow)
        }
    }

    return ReplyKeyboardMarkup(keyboardButtons).apply {
        resizeKeyboard = true
        oneTimeKeyboard = true
    }
}
