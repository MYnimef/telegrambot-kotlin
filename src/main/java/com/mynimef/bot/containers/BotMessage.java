package com.mynimef.bot.containers;

import java.util.ArrayList;
import java.util.List;


public final class BotMessage {

    private final String text;
    private boolean nextLine = true;
    private List<ButtonLine> buttonLines = new ArrayList<>();
    private List<BotFile> files = new ArrayList<>();

    public BotMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public boolean doesHaveButtons() {
        return buttonLines != null;
    }

    public List<ButtonLine> getButtons() {
        return buttonLines;
    }

    public void setButtons(List<ButtonLine> buttonLines) {
        this.buttonLines = buttonLines;
    }

    public BotMessage addButton(String label, String callbackId) {
        Button button = new Button(label, callbackId);

        if (nextLine) {
            nextLine = false;
            buttonLines.add(new ButtonLine(button));
        } else {
            buttonLines.get(buttonLines.size() - 1).addButton(button);
        }

        return this;
    }

    public BotMessage addLine() {
        nextLine = true;
        return this;
    }


    public List<BotFile> getFiles() { return files; }


    public void setFiles(List<BotFile> files) {
        this.files = files;
    }


    public BotMessage addFile(String path, String description) {
        files.add(new BotFile(path, description));
        return this;
    }
}
