package config

import actions.ICallback

abstract class CallbacksBuilder {

    private val callbacks: MutableMap<String, ICallback> = HashMap()

    protected abstract fun init()

    fun getCallbacks(): Map<String, ICallback> {
        init()
        return callbacks
    }

    protected fun add(callbackId: String, callback: ICallback) {
        callbacks[callbackId] = callback
    }

}
