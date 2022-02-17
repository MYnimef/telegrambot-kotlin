# Adding registration/states to your bot

### [Версия на русском языке](tut3_registration_ru.md)

### Adding states

Telegram doesn't save any user state so that is the thing we should. User state 
determine the bot reaction. Simple example - we want to add a registration to our bot 
to collect any data from users. For that purpose lets create enum class of states:

```java
public enum MyUserState {
    UNAUTHORIZED,
    SET_NAME, 
    SET_CITY,
    SET_AGE,
    AUTHORIZED
}
```

Let's analyze these states: 

- UNAUTHORIZED - the state of user that send a message for the first time;
- SET_NAME - bot expects user to input his name;
- SET_CITY - bot expects user to enter his city;
- SET_AGE - bot expects user to input his age;
- AUTHORIZED - the state when bot expects user to enter any command.

What should we do with this enum? Let

### Creating states handler

As we did our own class to add commands, we should create class that extends RegistrationBuilder:

```java
public class MyRegistrationBuilder extends RegistrationBuilder<MyUserState> {
    @Override
    public void initialize() {
        
    }
}
```

Let's add some states... 

```java
public class MyRegistrationBuilder extends RegistrationBuilder<MyUserState> {
    @Override
    public void initialize() {
        add(
                MyUserState.UNAUTHORIZED,
                "Who are you?"
        );
    }
}
```

We added a message that will receive all the users with UNAUTHORIZED state. Let's add more states:

```java
public class MyRegistrationBuilder extends RegistrationBuilder<MyUserState> {
    @Override
    public void initialize() {
        add(
                MyUserState.UNAUTHORIZED,
                "Who are you?"
        );

        add(
                MyUserState.SET_NAME,
                "Nice name, bro",
                "bruh",
                (input, chatId) -> { some logic with return type boolean }
        );

        add(
                MyUserState.SET_CITY,
                "I'm from the same town",
                "bruuuuuuuuuuuh",
                (input, chatId) -> { some logic with return type boolean }
        );

        add(
                MyUserState.SET_AGE,
                "Welcome to the club, buddy!",
                "bruuuuuuuuuuuuuuuuuuuuuuh",
                (input, chatId) -> { some logic with return type boolean }
        );
    }
}
```

Probably rn you have a lot of questions. It's fine. Coz of two reasons. First -I'm too bad at writing instructions, 
second - some of your answers will disappear later. One thing that should be said rn  - 
by saying 'logic' I mean the logic, that will change user state, and will return
true or false depending on whether you are satisfied with user input or no.

### Adding states to our bot

To add states to our bot, we should call the method addRegistration before start method call in Bot Initialization:

```java
public class Main {
    public static void main(String[] args) {
        IBot bot = new BotCreator(
                "token",
                "username"
        )
                .addCommands(new MyCommandsBuilder())
                .addRegistration(new MyRegistrationBuilder(), (chatId) -> { логика определения состояния пользователя }, MyUserState.AUTHORIZED)
                .start();
    }
}
```

We should add 3 parameters in addRegistration method:

- our class that extends RegistrationBuilder;
- lambda that returns user state by chatId. If user send message to the bot for the first time he actually can't have a state. 
So as solution I suggest return UNAUTHORIZED state in this expression and set to user SET_NAME state;
- commands handler state. In our case it's AUTHORIZED.

Congratulations, now our bot have registration!

to be continued...