package com.mynimef.bot.handlers;

import com.mynimef.bot.commands.*;
import com.mynimef.bot.containers.VMFile;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;

public class InputHandler extends Handler {
    protected final String message;
    protected final String username;
    protected final String firstName;
    protected final String lastName;

    protected final ICommand command;

    protected final SendMessage reply;

    @Override
    public SendMessage getReply() { return reply; }
    public List<SendDocument> getDocs() { return docs; }

    public InputHandler(
            String message,
            Long chatId,
            String username,
            String firstName,
            String lastName,
            Map<String, ICommand> commands
    ) {
        super(chatId, chatId.toString());
        this.message = message;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;

        this.command = commands.get(message);

        this.reply = new SendMessage();
        this.reply.setChatId(chatIdStr);
    }

    public IHandler start() {
        commandsHandler();
        return this;
    }

    protected void commandsHandler() {
        if (command != null) {
            reply.setText(command.getReply());
            boolean haveFiles = true;

            if (command instanceof ICommandAction) {
                ((ICommandAction) command)
                        .action(chatIdLong, username, firstName, lastName);
            } else if (command instanceof ICommandReply) {
                reply.setText(((ICommandReply) command)
                        .action(chatIdLong, username, firstName, lastName, command.getReply()));
            } else if (command instanceof ICommandFile) {
                haveFiles = ((ICommandFile) command)
                        .action(chatIdLong, username, firstName, lastName);
            }

            if (command.doesHaveFiles() && haveFiles) {
                for (VMFile file : command.getFiles()) {
                    addDoc(file, chatIdStr);
                }
            }

            if (command.doesHaveButtons()) {
                reply.setReplyMarkup(KeyboardBuilder.setReply(command.getButtons()));
            }
        } else {
            reply.setText("Ошибка ввода. Наберите /help для просмотра списка комманд");
        }
    }
}