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

}


