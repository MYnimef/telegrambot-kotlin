package com.mynimef.bot.commands;

import com.mynimef.bot.containers.IButtonsContainer;
import com.mynimef.bot.containers.IFileContainer;

public interface ICommand extends IFileContainer, IButtonsContainer {
    String getReply();
}