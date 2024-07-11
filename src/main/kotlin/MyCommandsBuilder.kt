import com.mynimef.bot.containers.UserCommand

internal class MyCommandsBuilder {

    @BotCommand("/start")
    fun sayHello(userCommand: UserCommand, bot: IBot) {
        bot.sendMessage(
            chatId = userCommand.chatId,
            text = "hi champ"
        )
    }

    @BotCommand("/bye")
    fun sayBye(userCommand: UserCommand, bot: IBot) {
        bot.sendMessage(
            chatId = userCommand.chatId,
            text = "bye champ"
        )
    }

}