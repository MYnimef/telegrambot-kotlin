package com.mynimef.telegrambot.containers


/**
 * Creates an instance of a text message for the Telegram Bot API.
 *
 * This function generates a `BotMessage.Text` object with the specified parameters,
 * which can be used to send a text message through the Telegram Bot API.
 *
 * @param text The text content of the message to be sent. This is a mandatory field.
 * @param disableNotification Optional. If set to `true`, the message will be sent silently.
 *                              Users will not receive a notification for this message.
 *                              Defaults to `false`.
 * @param replyToMessageId Optional. If specified, the message will be sent as a reply
 *                          to the message with the given ID. Defaults to `null`, which means
 *                          no reply is set.
 *
 * @return A `BotMessage.Text` instance containing the specified parameters.
 */
fun botMessage(
    text: String,
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null
) = BotMessage.Text(
    text = text,
    disableNotification = disableNotification,
    replyToMessageId = replyToMessageId
)

/**
 * Creates an instance of a file message for the Telegram Bot API.
 *
 * This function generates a `BotMessage.File` object with the specified parameters,
 * which can be used to send a file through the Telegram Bot API.
 *
 * @param file The file to be sent. This is a mandatory field and must be an instance of
 *             `java.io.File`.
 * @param description Optional. A description or caption for the file. This text will be
 *                     sent along with the file and can provide context or additional information.
 *                     Defaults to `null` if no description is provided.
 * @param disableNotification Optional. If set to `true`, the message will be sent silently.
 *                              Users will not receive a notification for this message.
 *                              Defaults to `false`.
 * @param replyToMessageId Optional. If specified, the message will be sent as a reply
 *                          to the message with the given ID. Defaults to `null`, which means
 *                          no reply is set.
 *
 * @return A `BotMessage.File` instance containing the specified parameters.
 */
fun botMessage(
    file: java.io.File,
    description: String? = null,
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null
) = BotMessage.File(
    file = file,
    description = description,
    disableNotification = disableNotification,
    replyToMessageId = replyToMessageId
)

/**
 * Creates an instance of a location message for the Telegram Bot API.
 *
 * This function generates a `BotMessage.Location` object with the specified parameters,
 * which can be used to send a location through the Telegram Bot API.
 *
 * @param latitude The latitude of the location to be sent. This is a mandatory field and
 *                 must be a valid geographic coordinate.
 * @param longitude The longitude of the location to be sent. This is a mandatory field and
 *                  must be a valid geographic coordinate.
 * @param disableNotification Optional. If set to `true`, the message will be sent silently.
 *                              Users will not receive a notification for this message.
 *                              Defaults to `false`.
 * @param replyToMessageId Optional. If specified, the message will be sent as a reply
 *                          to the message with the given ID. Defaults to `null`, which means
 *                          no reply is set.
 *
 * @return A `BotMessage.Location` instance containing the specified parameters.
 */
fun botMessage(
    latitude: Double,
    longitude: Double,
    disableNotification: Boolean = false,
    replyToMessageId: Int? = null
) = BotMessage.Location(
    latitude = latitude,
    longitude = longitude,
    disableNotification = disableNotification,
    replyToMessageId = replyToMessageId
)

/**
 * BotMessage to send to user
 */
sealed interface BotMessage {

    /**
     * Optional. Sends the message silently.
     * Users will receive a notification with no sound.
     */
    val disableNotification: Boolean

    val replyToMessageId: Int?

    /**
     * Simple text message
     */
    class Text @Throws(IllegalArgumentException::class) constructor(
        val text: String,
        override val disableNotification: Boolean = false,
        override val replyToMessageId: Int? = null
    ): BotMessage, Configurable() {
        init {
            if (text.isBlank()) {
                throw IllegalArgumentException("BotMessage.Text text property cannot be empty or blank")
            }
        }
    }

    /**
     * Message with file
     */
    class File(
        val file: java.io.File,
        val description: String? = null,
        override val disableNotification: Boolean = false,
        override val replyToMessageId: Int? = null
    ): BotMessage, Configurable()

    /**
     * Message with file
     */
    class Location(
        val latitude: Double,
        val longitude: Double,
        override val disableNotification: Boolean = false,
        override val replyToMessageId: Int? = null
    ): BotMessage, Configurable()

    sealed class Configurable: ButtonInline.Container, ButtonKeyboard.Container {

        internal var addOn: AddOn? = null

        override fun addButtonsLine(vararg buttons: ButtonInline): ButtonInline.Container {
            if (addOn == null) {
                addOn = AddOn.ButtonInlineContainer()
            }
            (addOn!! as AddOn.ButtonInlineContainer).inlineButtonLines.add(buttons.asList())
            return this
        }

        override fun addButtonsLines(lines: List<List<ButtonInline>>): ButtonInline.Container {
            if (addOn == null) {
                addOn = AddOn.ButtonInlineContainer()
            }
            (addOn!! as AddOn.ButtonInlineContainer).inlineButtonLines.addAll(lines)
            return this
        }

        override fun addButtonsLine(vararg buttons: ButtonKeyboard): ButtonKeyboard.Container {
            if (addOn == null) {
                addOn = AddOn.ButtonKeyboardContainer()
            }
            (addOn!! as AddOn.ButtonKeyboardContainer).keyboardButtonLines.add(buttons.asList())
            return this
        }

        override fun addButtonsLines(lines: List<List<ButtonKeyboard>>): ButtonKeyboard.Container {
            if (addOn == null) {
                addOn = AddOn.ButtonKeyboardContainer()
            }
            (addOn!! as AddOn.ButtonKeyboardContainer).keyboardButtonLines.addAll(lines)
            return this
        }

        fun addKeyboardButtonRemover(): BotMessage {
            addOn = AddOn.ButtonKeyboardRemover
            return this
        }

        internal sealed interface AddOn {

            class ButtonInlineContainer internal constructor(
                internal val inlineButtonLines: MutableList<List<ButtonInline>> = mutableListOf()
            ): AddOn

            class ButtonKeyboardContainer internal constructor(
                internal val keyboardButtonLines: MutableList<List<ButtonKeyboard>> = mutableListOf()
            ): AddOn

            object ButtonKeyboardRemover: AddOn

        }

    }

}