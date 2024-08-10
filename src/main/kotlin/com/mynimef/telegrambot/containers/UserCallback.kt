package com.mynimef.telegrambot.containers

data class UserCallback(

    val callbackId: String,

    val originalMessageId: Int,

    override val userId: String,

    override val username: String?,

    override val firstName: String?,

    override val lastName: String?

): UserUpdate
