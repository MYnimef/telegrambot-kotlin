package com.mynimef.telegrambot.containers


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

    internal var addOn: AddOn? = null

    fun addInlineButtonsSupport(): AddOn.ButtonInlineContainer {
        val container = AddOn.ButtonInlineContainer()
        addOn = container
        return container
    }

    fun addKeyboardButtonsSupport(): AddOn.ButtonKeyboardContainer {
        val container = AddOn.ButtonKeyboardContainer()
        addOn = container
        return container
    }

    fun addKeyboardButtonRemover() {
        addOn = AddOn.ButtonKeyboardRemover
    }

    sealed interface AddOn {

        data class ButtonInlineContainer(
            internal val inlineButtonLines: MutableList<List<ButtonInline>> = mutableListOf()
        ): AddOn {

            fun addButtonsLine(vararg buttons: ButtonInline): ButtonInlineContainer {
                inlineButtonLines.add(buttons.toList())
                return this
            }

            fun addButtonsLines(lines: List<List<ButtonInline>>): ButtonInlineContainer {
                inlineButtonLines.addAll(lines)
                return this
            }

        }

        data class ButtonKeyboardContainer(
            internal val keyboardButtonLines: MutableList<List<ButtonKeyboard>> = mutableListOf()
        ): AddOn {

            fun addButtonsLine(vararg buttons: ButtonKeyboard): ButtonKeyboardContainer {
                keyboardButtonLines.add(buttons.toList())
                return this
            }

            fun addButtonsLines(lines: List<List<ButtonKeyboard>>): ButtonKeyboardContainer {
                keyboardButtonLines.addAll(lines)
                return this
            }

        }

        data object ButtonKeyboardRemover: AddOn

    }

}