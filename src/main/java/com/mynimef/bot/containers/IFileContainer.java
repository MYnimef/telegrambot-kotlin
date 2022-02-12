package com.mynimef.bot.containers;

import java.util.List;

public interface IFileContainer {
    List<VMFile> getFiles();
    void setFiles(List<VMFile> files);
}
