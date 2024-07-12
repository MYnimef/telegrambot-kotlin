package com.mynimef.bot.containers

class ButtonLine(button: Button) {

    private val _line: MutableList<Button> = ArrayList<Button>().apply {
        add(button)
    }
    val line: List<Button> = _line

    fun addButton(button: Button) {
        _line.add(button)
    }

}