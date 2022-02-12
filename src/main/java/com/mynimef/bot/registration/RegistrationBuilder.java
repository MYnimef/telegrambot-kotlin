package com.mynimef.bot.registration;

import java.util.HashMap;
import java.util.Map;

public abstract class RegistrationBuilder<E extends Enum<E>> {
    private final Map<E, IStage> stages;

    public RegistrationBuilder() {
        this.stages = new HashMap<>();
    }

    public abstract void initialize();

    protected Stage add(E stage, String reply) {
        Stage st = new Stage(reply);
        stages.put(stage, st);
        return st;
    }

    protected Stage add(E stage, String positiveReply, String negativeReply, IUpdate update) {
        Stage st = new Stage(positiveReply, negativeReply, update);
        stages.put(stage, st);
        return st;
    }

    public Map<E, IStage> getStages() {
        return stages;
    }
}
