package com.mynimef.bot.callback;

import com.mynimef.bot.containers.IButtonsContainer;
import com.mynimef.bot.containers.IFileAdd;
import com.mynimef.bot.containers.IFileContainer;

public interface ICallback extends IFileContainer, IButtonsContainer, IFileAdd<ICallback> {
}
