package com.mynimef.bot.callback;

import com.mynimef.bot.message.IMessage;

public interface ICallbackSendMessage {
    void callback(Long id, IMessage message);
}
