package com.mynimef.telegrambot.containers


sealed interface ButtonKeyboard {

    val label: String

    /**
     * Keyboard button that is used to send the exact same text from user when pressed
     */
    data class Text(override val label: String): ButtonKeyboard

    /**
     * Keyboard button that is used to ask user for his contact
     */
    data class AskPhone(override val label: String): ButtonKeyboard

    interface Container {

        fun addButtonsLine(vararg buttons: ButtonKeyboard): Container

        fun addButtonsLines(lines: List<List<ButtonKeyboard>>): Container

    }

}
