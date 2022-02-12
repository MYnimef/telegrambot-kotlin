package com.mynimef.bot.callback;

import com.mynimef.bot.containers.ButtonLine;
import com.mynimef.bot.containers.IButtonsContainer;

public class KeepButtons {
    private final ICallbackEdit callback;
    private final IButtonsContainer container;

    public KeepButtons(ICallbackEdit callback, IButtonsContainer container) {
        this.callback = callback;
        this.container = container;
    }

    public ICallbackEdit getCallback() {
        return callback;
    }

    public ButtonLine[] getButtonLines() {
        return container.getButtons();
    }
}
