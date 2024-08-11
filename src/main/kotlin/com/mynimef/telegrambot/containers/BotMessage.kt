package com.mynimef.telegrambot.containers


/**
 * BotMessage to send to user
 */
sealed interface BotMessage: BotSendable {

    /**
     * Simple text message
     */
    class Text @Throws(IllegalArgumentException::class) constructor(
        val text: String,
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
    ): BotMessage, Configurable()

}


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
        return this as BotMessage
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