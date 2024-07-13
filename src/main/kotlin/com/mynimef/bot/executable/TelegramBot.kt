package com.mynimef.bot.executable

import com.mynimef.bot.IBot
import com.mynimef.bot.containers.*
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.meta.generics.TelegramClient
import java.io.Serializable


internal class TelegramBot(token: String): IBot {

    private val telegramClient: TelegramClient = OkHttpTelegramClient(token)

    private fun<T: Serializable> sendMessage(message: BotApiMethod<T>): Int? {
        try {
            val sentMessage = telegramClient.execute(message) as Message
            return sentMessage.messageId
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return null
    }

    private fun sendDoc(doc: SendDocument): Int? {
        try {
            val sendMessage = telegramClient.execute(doc)
            return sendMessage.messageId
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
        return null
    }

    override fun sendMessage(chatId: String, message: BotMessage): Int? {
        val sendMessage = SendMessage(chatId, message.text)
        if (message.buttonLines.isNotEmpty()) {
            sendMessage.replyMarkup = setButtons(message.buttonLines)
        }
        return sendMessage(sendMessage)
    }

    override fun sendMessage(chatId: String, botFile: BotFile) {
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

        if (message.buttonLines.isNotEmpty()) {
            editMessage.replyMarkup = setButtons(message.buttonLines)
        }
        sendMessage(editMessage)
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


private fun setButtons(buttonsLines: List<List<Button>>): InlineKeyboardMarkup {
    val keyboardButtons: MutableList<InlineKeyboardRow> = ArrayList()
    buttonsLines.forEach { buttonLine ->
        val keyboardButtonsRow = InlineKeyboardRow()
        buttonLine.forEach { button ->
            val inlineKeyboardButton = InlineKeyboardButton(button.label)
            inlineKeyboardButton.callbackData = button.callback
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