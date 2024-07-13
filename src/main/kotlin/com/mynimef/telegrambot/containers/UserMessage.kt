package com.mynimef.telegrambot.containers

data class UserMessage(

    val text: String,

    val chatId: String,

    val messageId: Int,

    val username: String?,

    val firstName: String?,

    val lastName: String?

)
