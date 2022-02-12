package com.mynimef.bot.containers;

public class Button {
    private final String label;
    private final String callback;

    public Button(String label, String callback) {
        this.label = label;
        this.callback = callback;
    }

    public String getLabel() {
        return label;
    }

    public String getCallback() {
        return callback;
    }
}