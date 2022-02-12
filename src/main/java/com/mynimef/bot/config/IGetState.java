package com.mynimef.bot.config;

public interface IGetState<E extends Enum<E>> {
    E getState(Long id);
}
