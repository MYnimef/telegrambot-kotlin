package com.mynimef.telegrambot.containers


/**
 * BotMessage to send to user
 */
sealed class BotMessage: ButtonInline.Container, ButtonKeyboard.Container {

    private val _inlineButtonLines: MutableList<List<ButtonInline>> = mutableListOf()
    internal val inlineButtonLines by lazy { _inlineButtonLines.toList() }

    override fun addButtonsLine(vararg buttons: ButtonInline): ButtonInline.Container {
        _inlineButtonLines.add(buttons.toList())
        return this
    }

    override fun addButtonsLines(lines: List<List<ButtonInline>>): ButtonInline.Container {
        _inlineButtonLines.addAll(lines)
        return this
    }

    private val _keyboardButtonLines: MutableList<List<ButtonKeyboard>> = mutableListOf()
    internal val keyboardButtonLines by lazy { _keyboardButtonLines.toList() }

    override fun addButtonsLine(vararg buttons: ButtonKeyboard): ButtonKeyboard.Container {
        _keyboardButtonLines.add(buttons.toList())
        return this
    }

    override fun addButtonsLines(lines: List<List<ButtonKeyboard>>): ButtonKeyboard.Container {
        _keyboardButtonLines.addAll(lines)
        return this
    }

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

}