plugins {
    kotlin("jvm") version "2.1.10"
}

group = "com.flip.urlshortener"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val jUnitVersion = "5.12.0"

    testImplementation(platform("org.junit:junit-bom:$jUnitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
