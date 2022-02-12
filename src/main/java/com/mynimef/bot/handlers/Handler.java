package com.mynimef.bot.handlers;

import com.mynimef.bot.containers.VMFile;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Handler implements IHandler {
    protected final Long chatIdLong;
    protected final String chatIdStr;

    protected BotApiMethod<?> reply;
    protected final List<SendDocument> docs;

    public Handler(Long chatIdLong, String chatIdStr) {
        this.chatIdLong = chatIdLong;
        this.chatIdStr = chatIdStr;
        this.docs = new ArrayList<>();
    }

    @Override
    public BotApiMethod<?> getReply() {
        return reply;
    }

    @Override
    public List<SendDocument> getDocs() {
        return docs;
    }

    @Override
    public void addDoc(VMFile file, String chatId) {
        SendDocument doc = new SendDocument();
        doc.setChatId(chatId);
        doc.setDocument(new InputFile(new File(file.path())));
        doc.setCaption(file.description());
        docs.add(doc);
    }
}
