package com.mynimef.telegrambot.containers


/**
 * Simple text message
 */
interface InlineButtonsContainer {
    fun addButtonsLine(vararg buttons: ButtonInline): InlineButtonsContainer
}

/**
 * Simple text message
 */
interface KeyboardButtonsContainer {
    fun addButtonsLine(vararg buttons: ButtonKeyboard): KeyboardButtonsContainer
}

/**
 * BotMessage to send to user
 */
sealed class BotMessage(

    messageButtonLines: List<List<ButtonInline>> = emptyList(),

    keyboardButtonLines: List<List<ButtonKeyboard>> = emptyList()

): InlineButtonsContainer, KeyboardButtonsContainer {

    /**
     * Simple text message
     */
    class Text(
        val text: String,
        messageButtonLines: List<List<ButtonInline>> = emptyList(),
        keyboardButtonLines: List<List<ButtonKeyboard>> = emptyList(),
    ): BotMessage(messageButtonLines, keyboardButtonLines) {
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

        /**
         * File to attach to message
         */
        val file: java.io.File,

        /**
         * Description of a file
         */
        val description: String? = null,

        messageButtonLines: List<List<ButtonInline>> = emptyList(),
        keyboardButtonLines: List<List<ButtonKeyboard>> = emptyList(),
    ): BotMessage(messageButtonLines, keyboardButtonLines)

    private val _inlineButtonLines = messageButtonLines.toMutableList()
    internal val inlineButtonLines by lazy { _inlineButtonLines.toList() }

    override fun addButtonsLine(vararg buttons: ButtonInline): InlineButtonsContainer {
        _inlineButtonLines.add(buttons.toList())
        return this
    }

    private val _keyboardButtonLines = keyboardButtonLines.toMutableList()
    internal val keyboardButtonLines by lazy { _keyboardButtonLines.toList() }

    override fun addButtonsLine(vararg buttons: ButtonKeyboard): KeyboardButtonsContainer {
        _keyboardButtonLines.add(buttons.toList())
        return this
    }

}