package com.mynimef.bot.config;

interface IMessageHandler {
    void handleMessage(String text, Long id, String username, String firstName, String lastName);
}
