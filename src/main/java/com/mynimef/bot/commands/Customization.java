package com.mynimef.bot.commands;

import com.mynimef.bot.callback.*;

import java.util.Map;
import java.util.UUID;

final class Customization implements ICustomization {
    private final ICustomizationCommand command;
    private final Map<Long, ICallback> callbacks;

    Customization(Command command, Map<Long, ICallback> callbacks) {
        this.command = command;
        this.callbacks = callbacks;
    }

    private ICustomization addButtonPrivate(String label, ICallback callback) {
        Long callbackId = UUID.randomUUID().getLeastSignificantBits();

        callbacks.put(callbackId, callback);
        command.addButton(label, String.valueOf(callbackId));

        return this;
    }

    @Override
    public ICustomization addButton(String label, ICallbackSendMessage callback) {
        return addButtonPrivate(label, callback);
    }

    @Override
    public ICustomization addButton(String label, ICallbackEdit callback) {
        return addButtonPrivate(label, callback);
    }

    @Override
    public ICustomization addButtonKeep(String label, ICallbackEdit callback) {
        return addButtonPrivate(label, (ICallbackEditKeep) () -> new KeepButtons(callback, command));
    }

    @Override
    public ICustomization addButton(String label, String text) {
        return addButtonPrivate(label, (ICallbackEdit) (chatId) -> text);
    }

    @Override
    public ICustomization addButtonKeep(String label, String text) {
        return addButtonPrivate(label, (ICallbackEditKeep) () -> new KeepButtons((chatId) -> text, command));
    }

    @Override
    public void addButtons(IMultipleLabels labels, ICallbackEditCustom callback) {
        for (String str: labels.callback()) {
            addButton(str, (chatId) -> callback.callback(chatId, str));
            addLine();
        }
    }

    @Override
    public ICustomization addLine() {
        command.addLine();
        return this;
    }

    @Override
    public ICustomization addFile(String path, String description) {
        command.addFile(path, description);
        return this;
    }
}
