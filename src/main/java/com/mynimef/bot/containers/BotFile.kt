package com.mynimef.bot.containers

data class BotFile(
    val path: String,
    val description: String
) {

    fun path(): String {
        return path
    }

    fun description(): String {
        return description
    }

}
