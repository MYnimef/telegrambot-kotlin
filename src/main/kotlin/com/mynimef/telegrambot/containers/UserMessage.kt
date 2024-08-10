package com.mynimef.telegrambot.containers

data class UserMessage(

    val text: String,

    val messageId: Int,

    override val userId: String,

    override val username: String?,

    override val firstName: String?,

    override val lastName: String?

): UserUpdate
