package com.mynimef.telegrambot.containers

import java.lang.ref.WeakReference


/**
 * BotMessage to send to user
 */
sealed class BotMessage {

    /**
     * Simple text message
     */
    class Text @Throws(IllegalArgumentException::class) constructor(
        val text: String,
    ): BotMessage() {
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
    ): BotMessage()

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    internal var addOn: AddOn? = null

    fun addInlineButtonsSupport(): AddOn.ButtonInlineContainer {
        val container = AddOn.ButtonInlineContainer(this)
        addOn = container
        return container
    }

    fun addKeyboardButtonsSupport(): AddOn.ButtonKeyboardContainer {
        val container = AddOn.ButtonKeyboardContainer(this)
        addOn = container
        return container
    }

    fun addKeyboardButtonRemover() {
        addOn = AddOn.ButtonKeyboardRemover(this)
    }

    sealed class AddOn(message: BotMessage) {

        internal val messageRef = WeakReference(message)

        class ButtonInlineContainer internal constructor(
            message: BotMessage,
            internal val inlineButtonLines: MutableList<List<ButtonInline>> = mutableListOf()
        ): AddOn(message) {

            fun addButtonsLine(vararg buttons: ButtonInline): ButtonInlineContainer {
                inlineButtonLines.add(buttons.toList())
                return this
            }

            fun addButtonsLines(lines: List<List<ButtonInline>>): ButtonInlineContainer {
                inlineButtonLines.addAll(lines)
                return this
            }

        }

        class ButtonKeyboardContainer internal constructor(
            message: BotMessage,
            internal val keyboardButtonLines: MutableList<List<ButtonKeyboard>> = mutableListOf()
        ): AddOn(message) {

            fun addButtonsLine(vararg buttons: ButtonKeyboard): ButtonKeyboardContainer {
                keyboardButtonLines.add(buttons.toList())
                return this
            }

            fun addButtonsLines(lines: List<List<ButtonKeyboard>>): ButtonKeyboardContainer {
                keyboardButtonLines.addAll(lines)
                return this
            }

        }

        class ButtonKeyboardRemover internal constructor(
            message: BotMessage
        ): AddOn(message)

    }

}