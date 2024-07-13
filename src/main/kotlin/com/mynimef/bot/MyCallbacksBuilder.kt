package com.mynimef.bot

import com.mynimef.bot.config.CallbacksBuilder

class MyCallbacksBuilder: CallbacksBuilder() {

    override fun init() {
        add(callbackId = "haha") { userCommand, bot ->
            bot.sendMessage(userCommand.chatId, "haha")
        }
    }

}