plugins {
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.1.1"
    application
}

application {
    mainClass.set("com.flip.urlshortener.AppKt")
}

group = "com.flip.urlshortener"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val jUnitVersion = "5.12.0"

    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-jackson")

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
