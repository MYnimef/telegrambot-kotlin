package com.mynimef.bot.callback;

public class CallbackEdit extends Callback implements ICallbackEdit {
    private final ICallbackEdit callback;

    public CallbackEdit(ICallbackEdit callback) {
        this.callback = callback;
    }

    @Override
    public String callback(Long id) {
        return callback.callback(id);
    }
}
