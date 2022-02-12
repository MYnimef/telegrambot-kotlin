package com.mynimef.bot.message;

import com.mynimef.bot.containers.IButtonsContainer;
import com.mynimef.bot.containers.IFileContainer;

public interface IMessage extends IFileContainer, IButtonsContainer {
    Message setText(String text);
    String getText();
}
