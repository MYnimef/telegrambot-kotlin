package com.mynimef.telegrambot

import com.mynimef.telegrambot.containers.BotSendable

interface IBot {

    fun sendMessage(chatId: String, text: String): Int?

    fun sendMessage(chatId: String, message: BotSendable): Int?

    fun editMessage(chatId: String, messageId: Int, message: BotSendable)

    fun editMessage(chatId: String, messageId: Int, text: String)

    fun deleteMessage(chatId: String, messageId: Int)

}
