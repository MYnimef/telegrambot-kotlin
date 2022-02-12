package com.mynimef.bot.callback;

public interface ICustomizationCallback {
    /**
     * Добавляет файл.
     */
    ICustomizationCallback addFile(String path, String description);
}
