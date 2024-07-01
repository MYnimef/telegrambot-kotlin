plugins {
    kotlin("jvm") version "2.0.0"
}

group "com.mynimef"
version "0.0.7"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    implementation("org.telegram:telegrambots:6.9.7.0")
    implementation("org.telegram:telegrambotsextensions:6.9.7.1")
}
