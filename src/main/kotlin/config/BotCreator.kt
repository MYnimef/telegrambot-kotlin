package config

import BotCommand
import IBot
import com.mynimef.bot.containers.UserCommand
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication


class BotCreator(private val token: String) {

    private var commands: MutableMap<String, Action> = HashMap()
    private var callbacks: CallbacksBuilder? = null

    private var logs: SaveLog? = null

    fun addCommands(commandsClass: Any): BotCreator {
        // Iterate through all declared functions in the class
        commandsClass.javaClass.declaredMethods.forEach { method ->
            val annotation = method.annotations.find { it is BotCommand } as BotCommand?
            if (
                annotation != null &&
                method.parameters.size == 2 &&
                method.parameters[0].type == UserCommand::class.java &&
                method.parameters[1].type == IBot::class.java &&
                method.returnType == Void.TYPE
            ) {
                val action: Action = { userCommand, bot ->
                    method.invoke(commandsClass, userCommand, bot)
                }
                annotation.commands.forEach { command ->
                    if (commands.containsKey(command)) {
                        //TODO
                    }
                    commands[command] = action
                }
            } else {
                throw Exception()
            }
        }
        return this
    }

    fun addCallbacks(callbacks: CallbacksBuilder?): BotCreator {
        this.callbacks = callbacks
        return this
    }

    fun addLogs(logs: SaveLog): BotCreator {
        this.logs = logs
        return this
    }

    fun start(): IBot? {
        return initBot(
            TelegramBot(
                commands = commands,
                noCommandRecognized = { ii, hh -> },
                callbacks = if ((callbacks != null)) callbacks!!.getCallbacks() else null,
                token = token,
                saveLog = logs
            )
        )
    }

    private fun initBot(bot: TelegramBot): TelegramBot? {
        try {
            TelegramBotsLongPollingApplication().use { botsApplication ->
                botsApplication.registerBot(token, bot)
                Thread.currentThread().join()
                return bot
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

}
