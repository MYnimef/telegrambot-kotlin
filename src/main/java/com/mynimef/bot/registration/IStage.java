package com.mynimef.bot.registration;

import com.mynimef.bot.containers.IFileContainer;

public interface IStage extends IFileContainer {
    String getReply(String input, Long chatId);
}
