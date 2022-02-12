package com.mynimef.bot.commands;

import com.mynimef.bot.callback.*;

public interface ICustomization {
    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomization addButton(String label, ICallbackSendMessage callback);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomization addButton(String label, ICallbackEdit callback);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomization addButtonKeep(String label, ICallbackEdit callback);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomization addButton(String label, String text);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomization addButtonKeep(String label, String text);

    /**
     * Добавляет новую линию для размещения на ней кнопок.
     */
    ICustomization addLine();

    /**
     * Добавляет файл.
     */
    ICustomization addFile(String path, String description);

    void addButtons(IMultipleLabels labels, ICallbackEditCustom callback);
}
