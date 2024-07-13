plugins {
    kotlin("jvm") version "2.0.0"
    `maven-publish`
    `java-library`
}

group = "com.github.MYnimef"
version = "0.0.1"

repositories {
    mavenCentral()
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

    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal()
    }
}
