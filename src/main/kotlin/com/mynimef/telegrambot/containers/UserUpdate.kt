package com.mynimef.telegrambot.containers

sealed interface UserUpdate {

    val userId: String

    val username: String?

    val firstName: String?

    val lastName: String?

}