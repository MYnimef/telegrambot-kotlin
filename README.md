[![](https://jitpack.io/v/MYnimef/telegrambot.svg)](https://jitpack.io/#MYnimef/telegrambot)

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

 Это пример добавления обычной команды. Позже будет дополнено. Затем, этот класс можно использовать при инициализации бота:

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
