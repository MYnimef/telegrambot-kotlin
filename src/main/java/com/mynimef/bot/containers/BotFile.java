package com.mynimef.bot.containers;

public class BotFile {
    private final String path;
    private final String description;

    public BotFile(String path, String description) {
        this.path = path;
        this.description = description;
    }

    public String path() {
        return path;
    }

    public String description() {
        return description;
    }
}
