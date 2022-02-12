package com.mynimef.bot.callback;

import com.mynimef.bot.message.IMessage;

public class CallbackSendMessage extends Callback implements ICallbackSendMessage {
    private final ICallbackSendMessage callback;

    public CallbackSendMessage(ICallbackSendMessage callback) {
        this.callback = callback;
    }

    @Override
    public void callback(Long id, IMessage message) {
        callback.callback(id, message);
    }
}
