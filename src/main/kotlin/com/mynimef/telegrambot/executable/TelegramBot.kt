package com.mynimef.telegrambot.executable

import com.mynimef.telegrambot.IBot
import com.mynimef.telegrambot.containers.BotMessage
import com.mynimef.telegrambot.containers.ButtonInline
import com.mynimef.telegrambot.containers.ButtonKeyboard
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMediaDocument
import org.telegram.telegrambots.meta.api.objects.message.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
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

    private fun sendEditMedia(editMessageMedia: EditMessageMedia) {
        try {
            telegramClient.execute(editMessageMedia)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    override fun sendMessage(userId: String, message: BotMessage): Int? {
        when (message) {
            is BotMessage.File -> {
                val doc = SendDocument(userId, InputFile(message.file))
                doc.caption = message.description
                if (message.inlineButtonLines.isNotEmpty()) {
                    doc.replyMarkup = setButtons(message.inlineButtonLines)
                } else if (message.keyboardButtonLines.isNotEmpty()) {
                    doc.replyMarkup = setButtons(message.keyboardButtonLines)
                }
                return sendDoc(doc)
            }
            is BotMessage.Text -> {
                val sendMessage = SendMessage(userId, message.text)
                if (message.inlineButtonLines.isNotEmpty()) {
                    sendMessage.replyMarkup = setButtons(message.inlineButtonLines)
                } else if (message.keyboardButtonLines.isNotEmpty()) {
                    sendMessage.replyMarkup = setButtons(message.keyboardButtonLines)
                }
                return sendMessage(sendMessage)
            }
        }
    }

    override fun sendMessage(userId: String, text: String): Int? {
        val message = SendMessage(
            userId,
            text
        )
        return sendMessage(message)
    }

    override fun editMessage(userId: String, messageId: Int, text: String) {
        val editMessage = EditMessageText(text)
        editMessage.chatId = userId
        editMessage.messageId = messageId
        sendMessage(editMessage)
    }

    override fun editMessage(userId: String, messageId: Int, message: BotMessage) {
        when (message) {
            is BotMessage.File -> {
                val editMessage = EditMessageMedia(InputMediaDocument(message.file, ""))
                editMessage.chatId = userId
                editMessage.messageId = messageId
                if (message.inlineButtonLines.isNotEmpty()) {
                    editMessage.replyMarkup = setButtons(message.inlineButtonLines)
                }
                sendEditMedia(editMessage)
            }
            is BotMessage.Text -> {
                val editMessage = EditMessageText(message.text)
                editMessage.chatId = userId
                editMessage.messageId = messageId
                if (message.inlineButtonLines.isNotEmpty()) {
                    editMessage.replyMarkup = setButtons(message.inlineButtonLines)
                }
                sendMessage(editMessage)
            }
        }
    }

    override fun deleteMessage(userId: String, messageId: Int) {
        val delete = DeleteMessage(userId, messageId)
        try {
            telegramClient.execute(delete)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

}


private fun setButtons(buttonsLines: List<List<ButtonInline>>): InlineKeyboardMarkup {
    val messageButtons: MutableList<InlineKeyboardRow> = mutableListOf()
    buttonsLines.forEach { buttonLine ->
        val messageButtonsRow = InlineKeyboardRow()
        buttonLine.forEach { button ->
            val inlineKeyboardButton = InlineKeyboardButton(button.label)
            when (button) {
                is ButtonInline.Callback -> inlineKeyboardButton.callbackData = button.callbackId
                is ButtonInline.URL -> inlineKeyboardButton.url = button.url
            }
            messageButtonsRow.add(inlineKeyboardButton)
        }
        messageButtons.add(messageButtonsRow)
    }
    return InlineKeyboardMarkup(messageButtons)
}


private fun setButtons(buttonsLines: List<List<ButtonKeyboard>>): ReplyKeyboardMarkup {
    val keyboardButtons: MutableList<KeyboardRow> = mutableListOf()
    buttonsLines.forEach { buttonLine ->
        val keyboardButtonsRow = KeyboardRow()
        buttonLine.forEach { button ->
            val keyboardButton = KeyboardButton(button.label)
            when (button) {
                is ButtonKeyboard.Text -> {}
                is ButtonKeyboard.AskPhone -> keyboardButton.requestContact = true
            }
            keyboardButtonsRow.add(keyboardButton)
        }
        keyboardButtons.add(keyboardButtonsRow)
    }

    return ReplyKeyboardMarkup(keyboardButtons).apply {
        resizeKeyboard = true
        oneTimeKeyboard = true
    }
}