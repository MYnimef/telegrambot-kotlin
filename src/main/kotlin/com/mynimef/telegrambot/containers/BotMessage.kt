package com.mynimef.telegrambot.containers


class BotMessage(

    val text: String,

    buttonLines: List<List<IButton>> = emptyList()

) {

    private val _buttonLines = buttonLines.toMutableList()

    internal val buttonLines: List<List<IButton>> = _buttonLines

    fun addButtonsLine(vararg buttons: IButton): BotMessage {
        _buttonLines.add(buttons.toList())
        return this
    }

}
