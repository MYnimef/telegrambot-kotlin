package com.mynimef.telegrambot.containers


sealed interface IButton {

    val label: String

}

data class ButtonCallback(

    override val label: String,

    val callbackId: String

): IButton

data class ButtonURL(

    override val label: String,

    val url: String

): IButton


