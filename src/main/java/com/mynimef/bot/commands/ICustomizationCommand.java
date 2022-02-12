package com.mynimef.bot.commands;

import com.mynimef.bot.callback.*;

public interface ICustomizationCommand {
    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomizationCommand addButton(String label, ICustomizationCallback callback);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomizationCommand addButtonKeep(String label, ICustomizationCallback callback);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomizationCommand addButton(String label, String text);

    /**
     * Добавляет новую кнопку на последнюю линию.
     */
    ICustomizationCommand addButtonKeep(String label, String text);

    /**
     * Добавляет новую линию для размещения на ней кнопок.
     */
    ICustomizationCommand addLine();

    /**
     * Добавляет файл.
     */
    ICustomizationCommand addFile(String path, String description);

    ICustomizationCommand addButtons(IMultipleLabels labels, ICustomizationCallback callback);

    ICustomizationCommand addButtonsKeep(IMultipleLabels labels, ICustomizationCallback callback);
}
