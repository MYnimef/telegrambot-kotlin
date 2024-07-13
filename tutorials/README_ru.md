[![](https://jitpack.io/v/MYnimef/telegrambot-java.svg)](https://jitpack.io/#MYnimef/telegrambot-kotlin) [![Supported version](https://img.shields.io/badge/Telegram%20Bot%20API-5.7-blue)](https://core.telegram.org/bots/api-changelog#january-31-2022)

### [English version](../README.md)

# API для создания telegram бота

Это API использует [Rubenlagus' library for Java](https://github.com/rubenlagus/TelegramBots).

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
    <artifactId>telegrambot-kotlin</artifactId>
    <version>0.0.1</version>
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
    
    implementation 'com.github.MYnimef:telegrambot-kotlin:0.0.1'
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

```kotlin
fun main() {
    val bot = BotCreator("token")
        .start()
}
```

## Кастомизация бота

* [Урок 1. Логирование](tut1_logs_ru.md)
* [Урок 2. Добавление команд](tut2_commands_ru.md)



## Credits

* [Rubenlagus](https://github.com/rubenlagus/) for Telegram Bot JAVA API
