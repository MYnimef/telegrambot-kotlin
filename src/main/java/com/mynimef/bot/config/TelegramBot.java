package com.mynimef.bot.config;


import com.mynimef.bot.IBot;
import com.mynimef.bot.actions.IAction;
import com.mynimef.bot.actions.ICallback;
import com.mynimef.bot.actions.ISaveLogs;
import com.mynimef.bot.containers.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


final class TelegramBot extends TelegramLongPollingBot implements IBot {

    private final Map<String, IAction> commands;
    private final IAction noCommandRecognized;
    private final Map<String, ICallback> callbacks;

    private final String username;

    private final ISaveLogs saveLog;

    TelegramBot(
            Map<String, IAction> commands,
            IAction noCommandRecognized,
            Map<String, ICallback> callbacks,
            String token,
            String username,
            ISaveLogs saveLog
    ) {
        super(token);
        this.commands = commands;
        this.noCommandRecognized = noCommandRecognized;
        this.callbacks = callbacks;
        this.username = username;
        this.saveLog = saveLog;
    }

    @Override
    public String getBotUsername() { return username; }

    @Override
    public void onUpdateReceived(Update update) {
        new Thread(() -> {
            if (update.hasMessage()) {
                Message message = update.getMessage();

                if (message.hasText()) {
                    onMessageReceived(message);
                }
            } else if (update.hasCallbackQuery()) {
                if (callbacks != null) {
                    onCallbackReceived(update.getCallbackQuery());
                } else {
                    sendMessage(
                            update.getCallbackQuery().getMessage().getChatId().toString(),
                            "There are no callbacks. Please, set them properly"
                    );
                }
            }
        }).start();
    }

    private void onMessageReceived(Message message) {
        String text = message.getText();
        Chat chat = message.getChat();
        String chatId = message.getChatId().toString();
        Integer messageId = message.getMessageId();
        String username = chat.getUserName();
        String firstName = chat.getFirstName();
        String lastName = chat.getLastName();

        if (saveLog != null) {
            saveLog.log(text, chatId, messageId, username, firstName, lastName);
        }
        IAction command = commands.get(text);
        if (command != null) {
            command.action(text, chatId, messageId, username, firstName, lastName, this);
        } else {
            noCommandRecognized.action(text, chatId, messageId, username, firstName, lastName, this);
        }
    }

    private Integer sendMessage(BotApiMethod<?> reply) {
        if (reply != null) {
            try {
                Message sentMessage = (Message) execute(reply);
                return sentMessage.getMessageId();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void sendDoc(SendDocument doc) {
        try {
            execute(doc);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer sendMessage(String chatId, BotMessage message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message.getText());

        if (message.doesHaveButtons()) {
            sendMessage.setReplyMarkup(setReply(message.getButtons()));
        }

        Integer messageId = sendMessage(sendMessage);
        for (BotFile file: message.getFiles()) {
            sendDoc(chatId, file);
        }
        return messageId;
    }

    @Override
    public void sendDoc(String chatId, BotFile file) {
        SendDocument doc = new SendDocument();
        doc.setChatId(chatId);
        doc.setDocument(new InputFile(new File(file.path())));
        doc.setCaption(file.description());
        sendDoc(doc);
    }

    private static InlineKeyboardMarkup setReply(List<ButtonLine> buttons) {
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

    private static ReplyKeyboardMarkup setKeyboard(ButtonKeyboardLine[] buttons) {
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

    @Override
    public Integer sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return sendMessage(message);
    }

    private void onCallbackReceived(CallbackQuery query) {
        String chatId = query.getMessage().getChatId().toString();
        Integer messageId = query.getMessage().getMessageId();
        String message = query.getMessage().getText();
        Chat chat = query.getMessage().getChat();
        String username = chat.getUserName();
        String firstName = chat.getFirstName();
        String lastName = chat.getLastName();


        ICallback callback = callbacks.get(query.getData());
        if (callback != null) {
            callback.callback(chatId, messageId, message, username, firstName, lastName, this);
        } else {
            sendMessage(chatId, "There are no callback \"" + query.getData() + "\"");
        }
    }

    @Override
    public void editMessage(String chatId, Integer messageId, String text) {
        EditMessageText editMessage = new EditMessageText();
        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(text);
        sendMessage(editMessage);
    }

    @Override
    public void editMessage(String chatId, Integer messageId, BotMessage message) {
        EditMessageText editMessage = new EditMessageText();

        editMessage.setChatId(chatId);
        editMessage.setMessageId(messageId);
        editMessage.setText(message.getText());

        if (message.doesHaveButtons()) {
            editMessage.setReplyMarkup(setReply(message.getButtons()));
        }

        sendMessage(editMessage);
        for (BotFile file: message.getFiles()) {
            sendDoc(chatId, file);
        }
    }

    @Override
    public void deleteMessage(String chatId, Integer messageId) {
        DeleteMessage delete = new DeleteMessage();
        delete.setChatId(chatId);
        delete.setMessageId(messageId);

        try {
            execute(delete);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
