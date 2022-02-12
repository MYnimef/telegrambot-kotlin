package com.mynimef.bot.handlers;

import com.mynimef.bot.containers.Button;
import com.mynimef.bot.containers.ButtonLine;
import com.mynimef.bot.containers.ButtonKeyboardLine;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

final class KeyboardBuilder {
    static InlineKeyboardMarkup setReply(ButtonLine[] buttons) {
        InlineKeyboardMarkup replyMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();

        for (ButtonLine line: buttons) {
            List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
            for (Button markup: line.getLine()) {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                inlineKeyboardButton.setText(markup.getLabel());
                inlineKeyboardButton.setCallbackData(markup.getCallback());
                keyboardButtonsRow.add(inlineKeyboardButton);
            }
            keyboardButtons.add(keyboardButtonsRow);
        }

        replyMarkup.setKeyboard(keyboardButtons);
        return replyMarkup;
    }

    static ReplyKeyboardMarkup setKeyboard(ButtonKeyboardLine[] buttons) {
        ReplyKeyboardMarkup replyMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardButtons = new ArrayList<>();

        for (ButtonKeyboardLine line: buttons) {
            KeyboardRow keyboardRow = new KeyboardRow();
            for (String text : line.getLine()) {
                keyboardRow.add(text);
                keyboardButtons.add(keyboardRow);
            }
        }

        replyMarkup.setKeyboard(keyboardButtons);
        replyMarkup.setResizeKeyboard(true);
        replyMarkup.setOneTimeKeyboard(true);
        return replyMarkup;
    }
}