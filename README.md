[![](https://jitpack.io/v/MYnimef/telegrambot-java.svg)](https://jitpack.io/#MYnimef/telegrambot-java)

# API для создания telegram бота

This API uses [Rubenlagus' library for Java](https://github.com/rubenlagus/TelegramBots).

## Добавление в свой проект

Для добавления Данного API в свой проект необходимо добавить зависимость.

#### MAVEN

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.MYnimef</groupId>
    <artifactId>telegrambot-java</artifactId>
    <version>0.0.1</version>
</dependency>
```

#### GRADLE

```kotlin
repositories { 
    
    ...
    
    maven { 
        url 'https://jitpack.io'
    }
}

dependencies {
    
    ...
    
    implementation 'com.github.MYnimef:telegrambot-java:0.0.1'
}
```

## Начало
 Инициализация бота происходит подобным образом:

```java
public class Main {
    public static void main(String[] args) {
        IBot bot = new BotCreator(
                "token",
                "username"
        )
                .start();
    }
}
```

 Для добавления своих команд нужно проделать несколько действий. Для начала создадим класс, который будет наследоваться от CommandsBuilder:

```java
public class MyCommandsBuilder extends CommandsBuilder {
    @Override
    public void initialize() {
        add(
                "/start", 
                "Привет!"
        );
    }
}
```

 Давайте попробуем добавить команду, которая будет выполнять какое-то действие, когда пользователь ее введет:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    public void initialize() {
        add(
                "/test", 
                "watch your console",
                (chatId, username, firstName, lastName) -> {
                    System.out.println("Hello, world!");
                }
        );
    }
}
```

Эта команда будет выводить в консоль "Hello, world!" каждый раз, когда любой пользователь ее введет. Обратите внимание на
параметры лямбда-выражения. Давайте разберем их подробнее:

- chatId - уникальное Id каждого пользователя. Если задумаетесь над ведением базы данных пользователей, то это значение 
будет отличным первичным ключом. тип значения - Long;
- username - значение типа String, не подходит для роли первичного ключа, так как его можно изменить,
однако удобен для идентификации;
- firstName - имя пользователя, которое задается в телеграмме;
- secondName - фамилия пользователя, которая задается в телеграмме (если ее не задать, возвращает имя пользователя).

Все эти значения вы сможете получить, если захотите добавить логику в обработчик комманды. 
Теперь, давайте разберем пример добавления команды, которая будет изменять текст своего ответа:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    public void initialize() {
        add(
                "/sayhi", 
                "Hello, ",
                (chatId, username, firstName, lastName, replyConst) -> {
                    return replyConst + firstName + "!";
                }
        );
    }
}
```

Данная команда возвращает пользователю приветствие с его именем.

Теперь, допустим, мы захотели добавить команду с кнопками, прикрепленными к ней. Хорошая новость - у нас есть такая возможность! 
Попробуем создать команду с двумя кнопками, при нажатии на которые текст исходного сообщения будет меняться:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    public void initialize() {
        add(
                "/buttons", 
                "this text will be replaced if you press one of the buttons"
        )
                .addButton("button1", "text from button1")
                .addButton("button2", "text from button2")
        ;
    }
}
```

Попробуйте проделать аналогичные действия и проверьте результат. Спойлер: будут созданы две кнопки, 
при нажатии на которые текст изменится, а кнопки внезапно пропадут.... 
Но что если мы хотели сделать так, чтобы кнопки не исчезали?... Решение есть!

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    public void initialize() {
        add(
                "/buttons", 
                "this text will be replaced if you press one of the buttons"
        )
                .addButtonKeep("button1", "text from button1")
                .addButtonKeep("button2", "text from button2")
        ;
    }
}
```

Теперь при нажатии на обе кнопки они не будут пропадать! А что если нам этого мало? Если при нажатии на кнопку должно происходить 
какое-то действие... как быть в этом случае???? Давайте попробуем разобраться:

```java
public class MyCommandsBuilder extends CommandsBuilder { 
    @Override 
    public void initialize() {
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
    }
}
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
  
 ## Credits

* [Rubenlagus](https://github.com/rubenlagus/) for Telegram Bot JAVA API
