plugins {
    kotlin("jvm") version "2.0.21"
    `maven-publish`
    `java-library`
}

group = "com.github.MYnimef"
version = "0.0.18"

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
    implementation("org.telegram:telegrambots-longpolling:8.2.0")
    implementation("org.telegram:telegrambots-client:8.2.0")

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
