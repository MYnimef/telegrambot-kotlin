package com.mynimef.bot.containers

import java.io.File

class BotMessage(

    val text: String,

    files: List<BotFile> = emptyList(),

    buttonLines: List<ButtonLine> = emptyList()

) {

    private val _files: MutableList<BotFile> = files.toMutableList()
    private val _buttonLines: MutableList<ButtonLine> = buttonLines.toMutableList()

    private var nextLine = true

    internal val files: List<BotFile> = _files
    internal val buttonLines: List<ButtonLine> = _buttonLines

    fun addButton(label: String, callbackId: String): BotMessage {
        val button = Button(label, callbackId)
        if (nextLine) {
            nextLine = false
            _buttonLines.add(ButtonLine(button))
        } else {
            _buttonLines[_buttonLines.size - 1].addButton(button)
        }
        return this
    }

    fun addLine(): BotMessage {
        nextLine = true
        return this
    }

    fun addFile(file: File, description: String?): BotMessage {
        _files.add(BotFile(file, description))
        return this
    }

}
