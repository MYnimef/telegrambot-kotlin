package com.mynimef.bot.message;

import com.mynimef.bot.containers.Container;

public class Message extends Container<Message> implements IMessage {
    private String text;

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String getText() {
        return text;
    }
}
