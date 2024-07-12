import com.mynimef.bot.containers.*

internal class MyCommandsBuilder {

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
                buttonLines = mutableListOf(
                    ButtonLine(Button(label = "hi" , callback = "gggh"))
                )
            )
        )

        bot.sendMessage(
            chatId = userCommand.chatId,
            text = "whoa"
        )
    }

}