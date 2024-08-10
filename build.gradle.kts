plugins {
    kotlin("jvm") version "2.0.10"
    `maven-publish`
    `java-library`
}

group = "com.github.MYnimef"
version = "0.0.3"

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
    implementation("org.telegram:telegrambots-longpolling:7.7.2")
    implementation("org.telegram:telegrambots-client:7.7.2")

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
