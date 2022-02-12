package com.mynimef.bot.callback;

import com.mynimef.bot.message.IMessage;

public interface ICallbackSendMessage extends ICallback {
    void callback(Long id, IMessage message);
}
