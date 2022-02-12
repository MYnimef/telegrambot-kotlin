package com.mynimef.bot.config;

import org.telegram.telegrambots.meta.api.objects.Message;

interface IMessageReceiver {
    void onMessageReceived(Message message);
}
