package com.mynimef.telegrambot.containers

data class UserContact(

    val contactInfo: ContactInfo,

    override val userId: String,

    override val username: String?,

    override val firstName: String?,

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
        val userId: String?,

    )

}