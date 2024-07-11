import config.BotCreator

fun main() {

    val bot = BotCreator(token = "insert_your_token_here")
        .addCommands(commandsClass = MyCommandsBuilder())
        .start()

}