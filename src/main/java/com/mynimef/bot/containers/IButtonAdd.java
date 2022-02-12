package com.mynimef.bot.containers;

public interface IButtonAdd<T> {
    T addButton(String label, String callbackId);
    T addLine();
}
