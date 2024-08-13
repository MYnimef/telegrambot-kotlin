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

    /**
     * Represents a container for bot messages that can include interactive keyboard buttons.
     */
    sealed interface Container: BotMessage {

        /**
         * Adds a single line of keyboard buttons to the message.
         *
         * This method appends a horizontal row of keyboard buttons to the message. Each button
         * is represented by an instance of `ButtonKeyboard`.
         *
         * @param buttons One or more `ButtonKeyboard` instances to be added in a single horizontal line.
         *                The order of the buttons in this parameter determines their order in the line.
         * @return The updated `Container` instance with the added line of buttons.
         */
        fun addButtonsLine(vararg buttons: ButtonKeyboard): Container

        /**
         * Adds multiple lines of keyboard buttons to the message.
         *
         * This method appends several horizontal rows of keyboard buttons to the message. Each row
         * is specified by a list of `ButtonKeyboard` buttons.
         *
         * @param lines A list of lists, where each inner list represents a row of `ButtonKeyboard` buttons.
         *              Each `ButtonKeyboard` in the inner lists will be displayed in a horizontal row.
         *              The order of the inner lists determines the order of the button rows.
         * @return The updated `Container` instance with the added lines of buttons.
         */
        fun addButtonsLines(lines: List<List<ButtonKeyboard>>): Container

    }

}
