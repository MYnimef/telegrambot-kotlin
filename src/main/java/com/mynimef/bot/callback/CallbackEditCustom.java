package com.mynimef.bot.callback;

public class CallbackEditCustom extends Callback implements ICallbackEditCustom {
    private final ICallbackEditCustom callback;

    public CallbackEditCustom(ICallbackEditCustom callback) {
        this.callback = callback;
    }

    @Override
    public String callback(Long id, String input) {
        return callback.callback(id, input);
    }
}
