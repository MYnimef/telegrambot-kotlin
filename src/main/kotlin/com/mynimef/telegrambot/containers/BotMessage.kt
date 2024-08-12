package com.mynimef.telegrambot.containers


/**
 * Creates simple text message
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
 * Creates message with attached file
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
 * Creates message with location
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