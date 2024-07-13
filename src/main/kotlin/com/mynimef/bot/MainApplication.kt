package com.mynimef.bot

import com.mynimef.bot.config.BotCreator


internal fun main() {

    val bot = BotCreator(token = "insert_your_token_here")
        .addCommands(commandsClass = MyBotCommands())
        .addCallbacks(builder = MyCallbacksBuilder())
        .start()

}