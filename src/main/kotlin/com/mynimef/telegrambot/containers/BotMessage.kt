package com.mynimef.telegrambot.containers


class BotMessage(

    val text: String,

    buttonLines: List<List<Button>> = emptyList()

) {

    private val _buttonLines = buttonLines.toMutableList()

    internal val buttonLines: List<List<Button>> = _buttonLines

    fun addButtonsLine(vararg buttons: Button): BotMessage {
        _buttonLines.add(buttons.toList())
        return this
    }

}
