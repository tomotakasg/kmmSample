buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.10")
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("io.realm:realm-gradle-plugin:7.0.8")
        classpath("com.google.gms:google-services:4.3.4")
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
}


allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}