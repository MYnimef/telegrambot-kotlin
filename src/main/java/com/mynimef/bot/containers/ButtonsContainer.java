package com.mynimef.bot.containers;

class ButtonsContainer<T> implements IButtonsContainer, IButtonAdd<T> {
    private boolean nextLine;
    private ButtonLine[] buttonLines;

    public ButtonsContainer() {
        this.nextLine = true;
    }

    @Override
    public boolean doesHaveButtons() {
        return buttonLines != null;
    }

    @Override
    public ButtonLine[] getButtons() {
        return buttonLines;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addButton(String label, String callbackId) {
        Button button = new Button(label, callbackId);

        if (nextLine) {
            addLineToLines();
            nextLine = false;
        }

        int num = this.buttonLines.length - 1;
        if (this.buttonLines[num] == null) {
            this.buttonLines[num] = new ButtonLine(button);
        } else {
            this.buttonLines[this.buttonLines.length - 1].addButton(button);
        }

        return (T) this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addLine() {
        nextLine = true;
        return (T) this;
    }

    private void addLineToLines() {
        if (buttonLines != null) {
            int length = this.buttonLines.length;
            ButtonLine[] buttonLines = new ButtonLine[length + 1];
            System.arraycopy(this.buttonLines, 0, buttonLines, 0, length);
            this.buttonLines = buttonLines;
        } else {
            this.buttonLines = new ButtonLine[1];
        }
    }
}
