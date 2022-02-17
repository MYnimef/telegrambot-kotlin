# Adding commands to your bot

### [Версия на русском языке](tut2_commands_ru.md)

In order to add your own commands you need to do a few things. First - create a class that extends CommandsBuilder:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    protected void initialize() {
    
    }
    
    @Override 
    protected String nonCommandUpdate(String message, Long chatId, String username, String firstName, String lastName) {
        return null;
    }
}
```

As you can see - you need to override two methods. initialize - method, where we will add all of our commands. 
nonCommandsUpdate - method that returns String - this is what user will receive if no command recognized. For example let's create
echo bot:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    protected void initialize() {
    
    }
    
    @Override 
    protected String nonCommandUpdate(String message, Long chatId, String username, String firstName, String lastName) {
        return message;
    }
}
```

This bot will repeat every message you will send to it. Now, let's add commands in initialize:

```java
public class MyCommandsBuilder extends CommandsBuilder {
    @Override
    protected void initialize() {
        add(
                "/start", 
                "Hi!"
        );
    }
    
    @Override 
    protected String nonCommandUpdate(String message, Long chatId, String username, String firstName, String lastName) {
        return message;
    }
}
```

Let's add the command that will perform an action when user will use it. The following code is still in the method
initialize:

```java
add(
        "/test", 
        "watch your console",
        (chatId, username, firstName, lastName) -> {
            System.out.println("Hello, world!");
        }
);
```

This command will print "Hello, world!" in console every time when it will be used. 
Now, lets add the command with custom reply text:

```java
add(
        "/sayhi", 
        "Hello, ",
        (chatId, username, firstName, lastName, replyConst) -> {
            return replyConst + firstName + "!";
        }
);
```

This command will return Hello, Name!

## Adding buttons

Imagine you want your command to have a buttons. Good news - you have the ability to add them!
Let's create a command with two buttons that will change the text when pressed:

```java
add(
        "/buttons", 
        "this text will be replaced if you press one of the buttons"
)
        .addButton("button1", "text from button1")
        .addButton("button2", "text from button2");
```

Try to use this code and check the result. Spoiler: two buttons will be created,
pressing on them will change the text and the buttons will disappear....
But what if we don' t want them to disappear?... There is a solution!

```java
add(
        "/buttons", 
        "this text will be replaced if you press one of the buttons"
)
        .addButtonKeep("button1", "text from button1")
        .addButtonKeep("button2", "text from button2");
```

Now pressing on both of them won't make them disappear! But what if we want more? If we want to perform an action
when button is pressed... what should we do???? Let's figure it out:

```java
add(
        "/feedback", 
        "please send us feedback of using this bot"
)
        .addButton(
                Emoji.LIKE,
                action(id -> {
                    System.out.println("someone liked us");
                    return "thanks buddy";
                })
        )
        .addButton(
                Emoji.DISLIKE,
                action(id -> {
                    System.out.println("someone disliked us");
                    userRepo.blockUserById(id);
                    return "mmmm, okay, user-moment";
                })
        )
        .addLine()
        .addButton(
                "wtf",
                action(id -> {
                    System.out.println("someone pressed debug button");
                    return "You're a developer, Luke!";
                })
        );
```

Looks like we did it. The most democratic command is created - now let's analyze what inside. First of all - yes,
this library does support the Emoji (only 6 of them lol, lets make a challenge - 1k stars on this repo, and I'll add all of them). 
Second - we need add action method call as a parameter while creating the button.
Soon you'll understand why.

Btw, you can make not only the button, that will edit message, but that can also send another one:

```java
add(
        "/newmes",
        "pressbutton"
)
        .addButtonKeep(
                "Press me",
                action((id, mes) -> {
                    mes.setText("hi");
                })
        );
```

## Adding files

For example, we want to send file with a message. Can we do that? Ofc we can. It's so simple as 
adding buttons.

```java
add(
        "/file", 
        "there are your file"
)
        .addFile("path", "description");
```

Imagine if a file that we want to send is creating every time command is used. To avoid any mistakes, we can do the following:

```java
add(
        "/genfile",
        "Generation of the doc...",
        (chatId, username, firstName, lastName) -> {
            lofic of creating the file;
            return true; //if everything is ok
        }
)
        .addFile("path", "description");
```

What if we want to send files after pressing the button? 
The answer is here:

```java
add(
        "/buttondoc",
        "Generation of the doc...",
        (chatId, username, firstName, lastName) -> {
            lofic of creating the file;
            return true; //if everything is ok
        }
)
        .addButton(
                "wtf",
                action(id -> {
                    System.out.println("someone pressed debug button");
                    return "You're a developer, Luke!";
                })
                    .addFile("path", "description");
        );
```

Later more info will be added. As a result, we can use our class in Bot Initialization:

```java
public class Main {
    public static void main(String[] args) {
        IBot bot = new BotCreator(
                "token",
                "username"
        )
                .addCommands(new MyCommandsBuilder())
                .start();
    }
}
```

to be continued...

[Lesson 3. Adding Stages/Registration](tut3_registration.md)
