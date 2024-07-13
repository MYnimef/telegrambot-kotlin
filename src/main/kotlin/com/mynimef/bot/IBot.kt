package com.mynimef.bot

import com.mynimef.bot.containers.BotFile
import com.mynimef.bot.containers.BotMessage

interface IBot {

    fun sendMessage(chatId: String, text: String): Int?

    fun sendMessage(chatId: String, message: BotMessage): Int?

    fun sendMessage(chatId: String, botFile: BotFile)

    fun editMessage(chatId: String, messageId: Int, message: BotMessage)

    fun editMessage(chatId: String, messageId: Int, text: String)

    fun deleteMessage(chatId: String, messageId: Int)

}
