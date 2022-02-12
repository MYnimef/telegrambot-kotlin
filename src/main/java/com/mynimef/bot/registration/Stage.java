package com.mynimef.bot.registration;

import com.mynimef.bot.containers.FileContainer;

public class Stage extends FileContainer<Stage> implements IStage {
    private final String positiveReply;
    private final String negativeReply;
    private final IUpdate update;

    public Stage(String positiveReply) {
        this.positiveReply = positiveReply;
        this.negativeReply = null;
        this.update = null;
    }

    public Stage(String positiveReply, String negativeReply, IUpdate update) {
        this.positiveReply = positiveReply;
        this.negativeReply = negativeReply;
        this.update = update;
    }

    @Override
    public String getReply(String input, Long chatId) {
        if (update != null) {
            return update.update(input, chatId) ? positiveReply : negativeReply;
        } else {
            return positiveReply;
        }
    }
}
