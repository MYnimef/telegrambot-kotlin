package com.mynimef.telegrambot.containers

/**
 * User update that comes from user to the bot
 */
sealed interface UserUpdate {

    val chatId: String
    val username: String?
    val firstName: String
    val lastName: String?

    /**
     * Represents a message sent by a user in a chat.
     *
     * This data class encapsulates details of a message, including its content, unique identifier,
     * and information about the user and chat involved.
     *
     * @property text The text content of the message. This field is mandatory and cannot be null.
     * @property messageId The unique identifier of the message within the chat. This field is mandatory
     *                     and should be unique for each message in the same chat.
     * @property chatId The unique identifier of the chat where the message was sent. This field is mandatory
     *                  and is used to associate the message with a specific chat.
     * @property username The username of the user who sent the message, if available. This field is optional
     *                    and can be `null` if the user does not have a username.
     * @property firstName The first name of the user who sent the message. This field is mandatory.
     * @property lastName The last name of the user who sent the message, if available. This field is optional
     *                    and can be `null` if the user does not have a last name.
     */
    data class Message internal constructor(
        val text: String,
        val messageId: Int,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate

    /**
     * Represents a command sent by a user in a chat.
     *
     * This data class encapsulates details of a message, including its content, unique identifier,
     * and information about the user and chat involved.
     *
     * @property text The text content of the message. This field is mandatory and cannot be null.
     * @property messageId The unique identifier of the message within the chat. This field is mandatory
     *                     and should be unique for each message in the same chat.
     * @property chatId The unique identifier of the chat where the message was sent. This field is mandatory
     *                  and is used to associate the message with a specific chat.
     * @property username The username of the user who sent the message, if available. This field is optional
     *                    and can be `null` if the user does not have a username.
     * @property firstName The first name of the user who sent the message. This field is mandatory.
     * @property lastName The last name of the user who sent the message, if available. This field is optional
     *                    and can be `null` if the user does not have a last name.
     */
    data class Command internal constructor(
        val command: String,
        val messageId: Int,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate

    /**
     * Represents a callback event triggered by a user interaction with an inline button in a chat.
     *
     * This data class encapsulates details about the callback, including its identifier, the original
     * message associated with the callback, and user information.
     *
     * @property callbackId The unique identifier for the callback event. This field is mandatory and
     *                      is used to distinguish between different callback events.
     * @property originalMessageId The unique identifier of the original message that triggered the callback.
     *                             This field is mandatory and is used to reference the message associated
     *                             with the callback.
     * @property chatId The unique identifier of the chat where the callback event occurred. This field is
     *                  mandatory and is used to associate the callback with a specific chat.
     * @property username The username of the user who triggered the callback, if available. This field is
     *                    optional and can be `null` if the user does not have a username.
     * @property firstName The first name of the user who triggered the callback. This field is mandatory.
     * @property lastName The last name of the user who triggered the callback, if available. This field is
     *                    optional and can be `null` if the user does not have a last name.
     */
    data class Callback internal constructor(
        val callbackId: String,
        val originalMessageId: Int,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate


    /**
     * Represents a contact information update in a chat.
     *
     * This data class encapsulates details about a contact that was shared or updated within a chat,
     * including the contact's information and the user who provided it.
     *
     * @property contactInfo The contact information. This field is mandatory and contains details
     *                       about the contact shared in the chat.
     * @property chatId The unique identifier of the chat where the contact information was shared.
     *                  This field is mandatory and is used to associate the contact with a specific chat.
     * @property username The username of the user who shared the contact, if available. This field is
     *                    optional and can be `null` if the user does not have a username.
     * @property firstName The first name of the user who shared the contact. This field is mandatory.
     * @property lastName The last name of the user who shared the contact, if available. This field is
     *                    optional and can be `null` if the user does not have a last name.
     */
    data class Contact internal constructor(
        val contactInfo: ContactInfo,
        override val chatId: String,
        override val username: String?,
        override val firstName: String,
        override val lastName: String?
    ): UserUpdate {

        /**
         * Represents detailed contact information.
         *
         * This nested data class holds specific information about a contact, such as their phone number,
         * name, and Telegram user ID.
         *
         * @property phoneNumber The contact's phone number. This field is mandatory.
         * @property firstName The contact's first name. This field is mandatory.
         * @property lastName Optional. The contact's last name, if available. This field can be `null`
         *                    if the contact does not have a last name.
         * @property userId Optional. The contact's user identifier in Telegram, if available. This field
         *                  can be `null` if the contact does not have a Telegram user ID.
         */
        data class ContactInfo internal constructor(
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