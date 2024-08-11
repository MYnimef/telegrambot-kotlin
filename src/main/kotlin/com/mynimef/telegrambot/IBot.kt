package com.mynimef.telegrambot

import com.mynimef.telegrambot.containers.BotMessage
import com.mynimef.telegrambot.containers.ButtonInline
import com.mynimef.telegrambot.containers.ButtonKeyboard

interface IBot {

    fun sendMessage(chatId: String, text: String): Int?

    fun sendMessage(chatId: String, message: BotMessage): Int?

    fun sendMessage(chatId: String, message: ButtonInline.Container): Int? {
        return sendMessage(chatId = chatId, message = message as BotMessage)
    }

    fun sendMessage(chatId: String, message: ButtonKeyboard.Container): Int? {
        return sendMessage(chatId = chatId, message = message as BotMessage)
    }

    fun editMessage(chatId: String, messageId: Int, message: BotMessage)

    fun editMessage(chatId: String, messageId: Int, text: String)

    fun deleteMessage(chatId: String, messageId: Int)

}
