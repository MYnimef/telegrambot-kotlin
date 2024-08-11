package com.mynimef.telegrambot

import com.mynimef.telegrambot.containers.BotMessage

interface IBot {

    fun sendMessage(userId: String, text: String): Int?

    fun sendMessage(userId: String, message: BotMessage): Int?

    fun editMessage(userId: String, messageId: Int, message: BotMessage)

    fun editMessage(userId: String, messageId: Int, text: String)

    fun deleteMessage(userId: String, messageId: Int)

}
