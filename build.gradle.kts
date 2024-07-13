plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
}

group "com.mynimef"
version "0.0.1"

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
    implementation("org.telegram:telegrambots-longpolling:7.7.0")
    implementation("org.telegram:telegrambots-client:7.7.0")
}
