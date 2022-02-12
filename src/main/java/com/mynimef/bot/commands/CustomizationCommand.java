package com.mynimef.bot.commands;

import com.mynimef.bot.callback.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

final class CustomizationCommand implements ICustomizationCommand {
    private final Command command;
    private final Map<Long, ICallback> callbacks;

    CustomizationCommand(Command command, Map<Long, ICallback> callbacks) {
        this.command = command;
        this.callbacks = callbacks;
    }

    private ICustomizationCommand addButtonPrivate(String label, ICallback callback) {
        Long callbackId = UUID.randomUUID().getLeastSignificantBits();

        callbacks.put(callbackId, callback);
        command.addButton(label, String.valueOf(callbackId));

        return this;
    }

    @Override
    public ICustomizationCommand addButton(String label, ICustomizationCallback callback) {
        return addButtonPrivate(label, ((CustomizationCallback) callback).getCallback());
    }

    @Override
    public ICustomizationCommand addButtonKeep(String label, ICustomizationCallback callback) {
        ICallback cb = ((CustomizationCallback) callback).getCallback();
        cb.setButtons(command.getButtons());
        return addButtonPrivate(label, cb);
    }

    @Override
    public ICustomizationCommand addButton(String label, String text) {
        return addButtonPrivate(label, new CallbackEdit(id -> text));
    }

    @Override
    public ICustomizationCommand addButtonKeep(String label, String text) {
        ICallback cb = new CallbackEdit(id -> text);
        cb.setButtons(command.getButtons());
        return addButtonPrivate(label, cb);
    }

    @Override
    public ICustomizationCommand addButtons(IMultipleLabels labels, ICustomizationCallback callback) {
        CallbackEditCustom cb = (CallbackEditCustom) ((CustomizationCallback) callback).getCallback();

        Map<String, String> map_to = new HashMap<>();
        labels.callback(map_to);

        for (Map.Entry<String, String> map: map_to.entrySet()) {
            ICallback cb_it = new CallbackEdit(chatId -> cb.callback(chatId, map.getValue()));
            cb_it.setFiles(cb.getFiles());
            addButton(map.getKey(), new CustomizationCallback (cb_it));
            addLine();
        }

        return this;
    }

    @Override
    public ICustomizationCommand addButtonsKeep(IMultipleLabels labels, ICustomizationCallback callback) {
        CallbackEditCustom cb = (CallbackEditCustom) ((CustomizationCallback) callback).getCallback();

        Map<String, String> map_to = new HashMap<>();
        labels.callback(map_to);

        for (Map.Entry<String, String> map: map_to.entrySet()) {
            ICallback cb_it = new CallbackEdit(chatId -> cb.callback(chatId, map.getValue()));
            cb_it.setFiles(cb.getFiles());
            cb_it.setButtons(command.getButtons());
            addButton(map.getKey(), new CustomizationCallback (cb_it));
            addLine();
        }

        return this;
    }

    @Override
    public ICustomizationCommand addLine() {
        command.addLine();
        return this;
    }

    @Override
    public ICustomizationCommand addFile(String path, String description) {
        command.addFile(path, description);
        return this;
    }
}
