# Adding logs to your bot

Let's add one method call to our bot initialization before calling method start:

```java
public class Main {
    public static void main(String[] args) {
        IBot bot = new BotCreator(
                "token",
                "username"
        )
                .addLogs((message, chatId, username, firstName, lastName) -> System.out.println(username + ": " + message))
                .start();
    }
}
```

To add logs you should use method addLogs. It takes a lambda expression. 
Let's analyze its parameters:

- message - text message that bot receives (String);
- chatId - unique ID of every user, would be the perfect primary key (Long);
- username - not that good to be the primary key (can be changed) (String);
- firstName - first name of the user (String);
- secondName - second name of the user (if there's no second name it returns first name) (String).

Try to use this code and send some messages to your bot.

[Lesson 2. Adding Commands](tut2_commands.md)
