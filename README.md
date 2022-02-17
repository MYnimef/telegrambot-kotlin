[![](https://jitpack.io/v/MYnimef/telegrambot-java.svg)](https://jitpack.io/#MYnimef/telegrambot-java) [![Supported version](https://img.shields.io/badge/Telegram%20Bot%20API-5.7-blue)](https://core.telegram.org/bots/api-changelog#january-31-2022)

### [Версия на русском языке](tutorials/README_ru.md)

# API for creating telegram bot

This API uses [Rubenlagus' library for Java](https://github.com/rubenlagus/TelegramBots).

## Adding to your project

To add this API to your project you need to add dependency.

#### MAVEN

If you use maven, add to pom.xml following text:

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

If you use gradle, add to build.gradle following text:

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

## Possible problems

If your bot receives/sends messages with text that isn't supported in ANSII, you should add to build.gradle the following lines:

```kotlin
compileJava.options.encoding = 'UTF-8'
compileTestJava.options.encoding = 'UTF-8'
```

## Bot initialization

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

## Bot customization

* [Lesson 1. Adding Logs](tutorials/tut1_logs.md)
* [Lesson 2. Adding Commands](tutorials/tut2_commands.md)
* [Lesson 3. Adding Stages/Registration](tutorials/tut3_registration.md)

 
  
 ## Credits

* [Rubenlagus](https://github.com/rubenlagus/) for Telegram Bot JAVA API
