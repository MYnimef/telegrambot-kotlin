package com.mynimef.bot.containers;

public class ButtonLine {
    private Button[] line;

    public ButtonLine(Button button) {
        this.line = new Button[]{ button };
    }

    public Button[] getLine() { return line; }

    public void addButton(Button button) {
        int length = this.line.length;
        Button[] line = new Button[length + 1];
        System.arraycopy(this.line, 0, line, 0, length);
        this.line = line;

        this.line[length] = button;
    }
}