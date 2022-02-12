package com.mynimef.bot.containers;

import java.util.List;

public interface IButtonsContainer {
    boolean doesHaveButtons();
    List<ButtonLine> getButtons();
    void setButtons(List<ButtonLine> buttonLines);
}
