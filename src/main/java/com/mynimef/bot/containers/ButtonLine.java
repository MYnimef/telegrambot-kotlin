package com.mynimef.bot.containers;

import java.util.ArrayList;
import java.util.List;

public class ButtonLine {

    private final List<Button> line;

    public ButtonLine(Button button) {
        this.line = new ArrayList<>();
        this.line.add(button);
    }

    public List<Button> getLine() { return line; }

    public void addButton(Button button) {
        this.line.add(button);
    }

}