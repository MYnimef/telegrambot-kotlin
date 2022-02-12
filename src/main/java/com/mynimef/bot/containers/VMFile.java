package com.mynimef.bot.containers;

public class VMFile {
    private final String path;
    private final String description;

    public VMFile(String path, String description) {
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
