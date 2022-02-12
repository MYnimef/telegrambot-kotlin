package com.mynimef.bot.commands;

import com.mynimef.bot.containers.IButtonAdd;
import com.mynimef.bot.containers.IButtonsContainer;
import com.mynimef.bot.containers.IFileAdd;

public interface ICustomizationCommand extends IFileAdd<ICustomizationCommand>, IButtonAdd<ICustomizationCommand>, IButtonsContainer {
}
