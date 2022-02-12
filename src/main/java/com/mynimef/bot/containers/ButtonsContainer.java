package com.mynimef.bot.containers;

import java.util.ArrayList;
import java.util.List;

class ButtonsContainer<T> implements IButtonsContainer, IButtonAdd<T> {
    private boolean nextLine;
    private List<ButtonLine> buttonLines;

    public ButtonsContainer() {
        this.nextLine = true;
        this.buttonLines = new ArrayList<>();
    }

    @Override
    public boolean doesHaveButtons() {
        return buttonLines != null;
    }

    @Override
    public List<ButtonLine> getButtons() {
        return buttonLines;
    }

    @Override
    public void setButtons(List<ButtonLine> buttonLines) {
        this.buttonLines = buttonLines;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addButton(String label, String callbackId) {
        Button button = new Button(label, callbackId);

        if (nextLine) {
            nextLine = false;
            buttonLines.add(new ButtonLine(button));
        } else {
            buttonLines.get(buttonLines.size() - 1).addButton(button);
        }

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addLine() {
        nextLine = true;
        return (T) this;
    }
}
