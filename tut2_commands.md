# Добавление команд

Для добавления своих команд нужно проделать несколько действий. Для начала создадим класс, который будет наследоваться от CommandsBuilder:

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

Как мы видим, в нем надо обязательно имплементировать (реализовать) два метода. initialize - метод, в котором мы будем добавлять все свои команды. 
nonCommandsUpdate - то, что вернется пользователю, в случае, если ни одна из команд не будет распознана. Давайте для примера создадим 
эхо-бота:

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

Этот бот будет повторять все сообщения, которые вы будете ему присылать. Теперь попробуем добавить свои команды в метод initialize:

```java
public class MyCommandsBuilder extends CommandsBuilder {
    @Override
    protected void initialize() {
        add(
                "/start", 
                "Привет!"
        );
    }
    
    @Override 
    protected String nonCommandUpdate(String message, Long chatId, String username, String firstName, String lastName) {
        return message;
    }
}
```

Давайте попробуем добавить команду, которая будет выполнять какое-то действие, когда пользователь ее введет. Делаем это все еще в методе 
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

Эта команда будет выводить в консоль "Hello, world!" каждый раз, когда любой пользователь ее введет. Обратите внимание на
параметры лямбда-выражения. Давайте разберем их подробнее:

- chatId - уникальное Id каждого пользователя. Если задумаетесь над ведением базы данных пользователей, то это значение
  будет отличным первичным ключом. тип значения - Long;
- username - значение типа String, не подходит для роли первичного ключа, так как его можно изменить,
  однако удобен для идентификации;
- firstName - имя пользователя, которое задается в телеграмме;
- secondName - фамилия пользователя, которая задается в телеграмме (если ее не задать, возвращает имя пользователя).

Все эти значения вы сможете получить, если захотите добавить логику в обработчик команды.
Теперь, давайте разберем пример добавления команды, которая будет изменять текст своего ответа:

```java
add(
        "/sayhi", 
        "Hello, ",
        (chatId, username, firstName, lastName, replyConst) -> {
            return replyConst + firstName + "!";
        }
);
```

Данная команда возвращает пользователю приветствие с его именем.

Теперь, допустим, мы захотели добавить команду с кнопками, прикрепленными к ней. Хорошая новость - у нас есть такая возможность!
Попробуем создать команду с двумя кнопками, при нажатии на которые текст исходного сообщения будет меняться:

```java
add(
        "/buttons", 
        "this text will be replaced if you press one of the buttons"
)
        .addButton("button1", "text from button1")
        .addButton("button2", "text from button2");
```

Попробуйте проделать аналогичные действия и проверьте результат. Спойлер: будут созданы две кнопки,
при нажатии на которые текст изменится, а кнопки внезапно пропадут....
Но что если мы хотели сделать так, чтобы кнопки не исчезали?... Решение есть!

```java
add(
        "/buttons", 
        "this text will be replaced if you press one of the buttons"
)
        .addButtonKeep("button1", "text from button1")
        .addButtonKeep("button2", "text from button2");
```

Теперь при нажатии на обе кнопки они не будут пропадать! А что если нам этого мало? Если при нажатии на кнопку должно происходить
какое-то действие... как быть в этом случае???? Давайте попробуем разобраться:

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

Фух, вроде вышло. Самая демократическая команда создана, настало время разбирать, как это работает. Во-первых - да,
данная библиотека содержит в себе эмодзи (пока что только 6 штук - давайте челлендж - 1000 звездочек на этом репозитории
и я добавляю всю библиотеку эмодзи). Второе - да, нужно вызывать метод action для добавления обработчика нажатия кнопки.
Позже вы поймете зачем.

Позже будет дополнено. Затем, этот класс можно использовать при инициализации бота:

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

[Урок 3. Добавление стадий/регистрации](tut3_registration.md)
