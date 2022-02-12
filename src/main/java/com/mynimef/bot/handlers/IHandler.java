package com.mynimef.bot.handlers;

import com.mynimef.bot.containers.VMFile;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

import java.util.List;

public interface IHandler {
    IHandler start();
    BotApiMethod<?> getReply();
    void addDoc(VMFile file, String chatId);
    List<SendDocument> getDocs();
}