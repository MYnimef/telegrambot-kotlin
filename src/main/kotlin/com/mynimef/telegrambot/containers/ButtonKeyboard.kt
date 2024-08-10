package com.mynimef.telegrambot.containers


sealed interface ButtonKeyboard {

    val label: String

    data class AskPhone(

        override val label: String,

    ): ButtonKeyboard

}
