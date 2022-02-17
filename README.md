[![](https://jitpack.io/v/MYnimef/telegrambot-java.svg)](https://jitpack.io/#MYnimef/telegrambot-java) [![Supported version](https://img.shields.io/badge/Telegram%20Bot%20API-5.7-blue)](https://core.telegram.org/bots/api-changelog#january-31-2022)

# API для создания telegram бота

This API uses [Rubenlagus' library for Java](https://github.com/rubenlagus/TelegramBots).

## Добавление в свой проект

Для добавления Данного API в свой проект необходимо добавить зависимость.

#### MAVEN

Если в вашем проекте используется maven, добавьте в pom.xml следующее:

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
    <version>0.0.2</version>
</dependency>
```

#### GRADLE

Если в вашем проекте используется gradle, добавьте в build.gradle следующее:

```kotlin
repositories { 
    
    ...
    
    maven { 
        url 'https://jitpack.io'
    }
}

dependencies {
    
    ...
    
    implementation 'com.github.MYnimef:telegrambot-java:0.0.2'
}
```

## Возможные проблемы

Если ваш бот будет принимать/отправлять сообщения на русском языке, могут возникнуть проблемы с кодировкой. Для решения 
этих проблем просто добавьте в build.gradle следующее:

```kotlin
compileJava.options.encoding = 'UTF-8'
compileTestJava.options.encoding = 'UTF-8'
```

## Инициализация бота

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

## Кастомизация бота

* [Урок 1. Логирование](tutorials/tut1_logs.md)
* [Урок 2. Добавление команд](tutorials/tut2_commands.md)
* [Урок 3. Добавление стадий/регистрации](tutorials/tut3_registration.md)

 
  
 ## Credits

* [Rubenlagus](https://github.com/rubenlagus/) for Telegram Bot JAVA API
