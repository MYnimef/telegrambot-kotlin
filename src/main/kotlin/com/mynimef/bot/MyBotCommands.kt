package com.mynimef.bot

import com.mynimef.bot.containers.BotMessage
import com.mynimef.bot.containers.Button
import com.mynimef.bot.containers.UserCommand


internal class MyBotCommands {

    @BotCommand("/start")
    fun sayHello(userCommand: UserCommand, bot: IBot) {
        bot.sendMessage(
            chatId = userCommand.chatId,
            text = "hi champ"
        )
    }

    @BotCommand("/bye", "/bye")
    fun sayBye(userCommand: UserCommand, bot: IBot) {
        bot.sendMessage(
            chatId = userCommand.chatId,
            message = BotMessage(
                text = "dada",
            )
                .addButtonsLine(
                    Button(label = "great", callback = "haha"),
                    Button(label = "nope", callback = "wj"),
                )
        )

        bot.sendMessage(
            chatId = userCommand.chatId,
            text = "whoa"
        )
    }

}