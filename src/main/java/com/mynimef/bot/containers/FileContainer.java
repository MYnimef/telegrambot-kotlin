package com.mynimef.bot.containers;

public class FileContainer<T> implements IFileContainer, IFileAdd<T> {
    private VMFile[] files;

    @Override
    public boolean doesHaveFiles() { return files != null; }
    @Override
    public VMFile[] getFiles() { return files; }

    @SuppressWarnings("unchecked")
    @Override
    public T addFile(String path, String description) {
        VMFile file = new VMFile(path, description);

        if (this.files == null) {
            files = new VMFile[]{ file };
        } else {
            int length = this.files.length;
            VMFile[] files = new VMFile[length + 1];
            System.arraycopy(this.files, 0, files, 0, length);
            this.files = files;
            this.files[length] = file;
        }

        return (T) this;
    }
}
