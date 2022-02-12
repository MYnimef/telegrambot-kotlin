package com.mynimef.bot.callback;

import com.mynimef.bot.containers.ButtonLine;

import java.util.List;

public class CustomizationCallback implements ICustomizationCallback {
    private final ICallback callback;

    public CustomizationCallback(ICallback callback) {
        this.callback = callback;
    }

    @Override
    public ICustomizationCallback addFile(String path, String description) {
        callback.addFile(path, description);
        return this;
    }

    public ICallback setButtons(List<ButtonLine> buttonLines) {
        callback.setButtons(buttonLines);
        return callback;
    }

    public ICallback getCallback() {
        return callback;
    }
}
