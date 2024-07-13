package com.mynimef.bot.config

import com.mynimef.bot.IBot
import com.mynimef.bot.containers.UserCommand
import com.mynimef.bot.executable.Action


abstract class CallbacksBuilder {

    private val _callbacks: MutableMap<String, Action> = HashMap()
    internal val callbacks: Map<String, (command: UserCommand, bot: IBot) -> Unit> by lazy {
        init()
        _callbacks
    }

    protected abstract fun init()

    protected fun add(callbackId: String, action: Action) {
        _callbacks[callbackId] = action
    }

}
