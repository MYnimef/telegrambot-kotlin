package com.mynimef.bot.config

import com.mynimef.bot.executable.ActionCallback
import okhttp3.internal.toImmutableMap


abstract class CallbacksBuilder {

    private val _callbacks: MutableMap<String, ActionCallback> = HashMap()
    internal val callbacks by lazy {
        init()
        _callbacks.toImmutableMap()
    }

    protected abstract fun init()

    protected fun add(callbackId: String, action: ActionCallback) {
        _callbacks[callbackId] = action
    }

}
