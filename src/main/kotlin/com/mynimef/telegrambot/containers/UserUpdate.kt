package com.mynimef.telegrambot.containers

sealed interface UserUpdate {

    val chatId: String
    val username: String?
    val firstName: String
    val lastName: String?

    data class Message(
        val text: String,
        val messageId: Int,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate

    data class Callback(
        val callbackId: String,
        val originalMessageId: Int,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate

    data class Contact(
        val contactInfo: ContactInfo,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate {
        data class ContactInfo(
            /**
             * Contact's phone number
             */
            val phoneNumber: String,
            /**
             * Contact's first name
             */
            val firstName: String,
            /**
             * Optional.
             * Contact's last name
             */
            val lastName: String?,
            /**
             * Optional.
             * Contact's user identifier in Telegram
             */
            val userId: String?
        )
    }

}