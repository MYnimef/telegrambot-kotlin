package com.mynimef.bot.config;

import com.mynimef.bot.actions.ICallback;

import java.util.HashMap;
import java.util.Map;

public abstract class CallbacksBuilder {
    private final Map<String, ICallback> callbacks;

    public CallbacksBuilder() {
        this.callbacks = new HashMap<>();
    }

    protected abstract void init();

    public Map<String, ICallback> getCallbacks() {
        init();
        return callbacks;
    }

    protected void add(String callbackId, ICallback callback) {
        callbacks.put(callbackId, callback);
    }
}
