package com.mynimef.bot.containers;

import java.util.ArrayList;
import java.util.List;

public class FileContainer<T> implements IFileContainer, IFileAdd<T> {
    private List<VMFile> files;

    public FileContainer() {
        this.files = new ArrayList<>();
    }

    @Override
    public List<VMFile> getFiles() { return files; }

    @Override
    public void setFiles(List<VMFile> files) {
        this.files = files;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T addFile(String path, String description) {
        files.add(new VMFile(path, description));

        return (T) this;
    }
}
