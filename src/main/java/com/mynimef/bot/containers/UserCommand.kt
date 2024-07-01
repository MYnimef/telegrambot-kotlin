package com.mynimef.bot.containers

data class UserCommand(

    val text: String,

    val chatId: String,

    val messageId: Int,

    val username: String?,

    val firstName: String?,

    val lastName: String?

)
