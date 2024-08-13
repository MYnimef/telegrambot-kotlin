package com.mynimef.telegrambot.containers


sealed interface ButtonInline {

    val label: String

    data class Callback(

        override val label: String,

        val callbackId: String

    ): ButtonInline

    data class URL(

        override val label: String,

        val url: String

    ): ButtonInline

    /**
     * Represents a container for bot messages that can include interactive buttons.
     */
    sealed interface Container: BotMessage {

        /**
         * Adds a single line of inline buttons to the message.
         *
         * This method appends a horizontal row of inline buttons to the message. Each button
         * is represented by an instance of `ButtonInline`.
         *
         * @param buttons One or more `ButtonInline` instances to be added in a single horizontal line.
         *                The order of the buttons in this parameter determines their order in the line.
         * @return The updated `Container` instance with the added line of buttons.
         */
        fun addButtonsLine(vararg buttons: ButtonInline): Container

        /**
         * Adds multiple lines of inline buttons to the message.
         *
         * This method appends several horizontal rows of inline buttons to the message. Each row
         * is specified by a list of `ButtonInline` buttons.
         *
         * @param lines A list of lists, where each inner list represents a row of `ButtonInline` buttons.
         *              Each `ButtonInline` in the inner lists will be displayed in a horizontal row.
         *              The order of the inner lists determines the order of the button rows.
         * @return The updated `Container` instance with the added lines of buttons.
         */
        fun addButtonsLines(lines: List<List<ButtonInline>>): Container

    }

}
