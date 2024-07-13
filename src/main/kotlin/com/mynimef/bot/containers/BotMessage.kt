package com.mynimef.bot.containers

import java.io.File


class BotMessage(

    val text: String,

    files: List<BotFile> = emptyList(),

    buttonLines: List<List<Button>> = emptyList()

) {

    private val _files = files.toMutableList()
    private val _buttonLines = buttonLines.toMutableList()

    internal val files: List<BotFile> = _files
    internal val buttonLines: List<List<Button>> = _buttonLines

    fun addButtonsLine(vararg buttons: Button): BotMessage {
        _buttonLines.add(buttons.toList())
        return this
    }

    fun addFile(file: File, description: String?): BotMessage {
        _files.add(BotFile(file, description))
        return this
    }

}
