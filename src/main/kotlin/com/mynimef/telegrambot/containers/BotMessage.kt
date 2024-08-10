package com.mynimef.telegrambot.containers


class BotMessage(

    val text: String,

    messageButtonLines: List<List<ButtonInline>> = emptyList(),

    keyboardButtonLines: List<List<ButtonKeyboard>> = emptyList()

) {

    init {
        if (text.isBlank()) {
            throw IllegalArgumentException("BotMessage text cannot be empty or blank")
        }
    }

    private val _inlineButtonLines = messageButtonLines.toMutableList()
    internal val inlineButtonLines by lazy { _inlineButtonLines.toList() }

    @Throws(IllegalArgumentException::class)
    fun addButtonsLine(vararg buttons: ButtonInline): BotMessage {
        if (_keyboardButtonLines.isNotEmpty()) {
            throw IllegalArgumentException("Can't support both of the features. BotMessage keyboard buttons is not Empty")
        }
        _inlineButtonLines.add(buttons.toList())
        return this
    }

    private val _keyboardButtonLines = keyboardButtonLines.toMutableList()
    internal val keyboardButtonLines by lazy { _keyboardButtonLines.toList() }

    @Throws(IllegalArgumentException::class)
    fun addButtonsLine(vararg buttons: ButtonKeyboard): BotMessage {
        if (_inlineButtonLines.isNotEmpty()) {
            throw IllegalArgumentException("Can't support both of the features. BotMessage inline buttons is not Empty")
        }
        _keyboardButtonLines.add(buttons.toList())
        return this
    }

}
