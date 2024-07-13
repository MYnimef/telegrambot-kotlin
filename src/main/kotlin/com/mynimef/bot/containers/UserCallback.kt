package com.mynimef.bot.containers

data class UserCallback(

    val callbackId: String,

    val chatId: String,

    val messageId: Int,

    val username: String?,

    val firstName: String?,

    val lastName: String?

)
