import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
//import org.gradle.api.plugins.ExtensionAware
//
//import org.junit.platform.gradle.plugin.FiltersExtension
//import org.junit.platform.gradle.plugin.EnginesExtension
//import org.junit.platform.gradle.plugin.JUnitPlatformExtension
//import de.mannodermaus.gradle.plugins.junit5.AndroidJUnitPlatformPlugin
//import de.mannodermaus.gradle.plugins.junit5.junitPlatform
//
//import org.gradle.jvm.tasks.Jar
//import org.gradle.kotlin.dsl.kotlin
//import org.gradle.kotlin.dsl.version
//import org.jetbrains.kotlin.gradle.dsl.Coroutines

/**
 * FYI:https://subroh0508.net/articles/jacoco-scripts-in-anroid-muitl-module-project-by-kotlin-dsl
 */

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-android-extensions")
   // id("realm-kotlin") // レルムはKMMで人気出るかも・・
    //ビジネスロジックがsharedに集約するはずだから、ここにだけテストを入れる
   // jacoco
}

//tasks.jacocoTestReport {
//    reports {
//        xml.isEnabled = false
//        csv.isEnabled = false
//        html.destination = file("${buildDir}/jacocoHtml")
//    }
//}


// setup the plugin
//buildscript {
//    dependencies {
//        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.0")
//    }
//}

val ktor_version = "1.4.0"
kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }
    sourceSets {
        val commonMain by getting    {
            dependencies {
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
                implementation("io.ktor:ktor-client-json:1.4.1")
                implementation("io.ktor:ktor-client-serialization:1.4.1")
                implementation("io.ktor:ktor-client-android:1.0.1")
                implementation("io.ktor:ktor-client-gson:1.4.1")
                implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.3.0")
                //implementation("io.realm.kotlin:library:0.0.1-SNAPSHOT")
//                testm("org.jetbrains.spek:spek-api:1.1.5")
//                testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
                implementation("io.ktor:ktor-client-android:1.4.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val iosMain by getting   {
            dependencies {
                implementation("io.ktor:ktor-client-android:1.4.1")
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}


// extension for configuration
//fun JUnitPlatformExtension.filters(setup: FiltersExtension.() -> Unit) {
//    when (this) {
//        is ExtensionAware -> extensions.getByType(FiltersExtension::class.java).setup()
//        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
//    }
//}
//fun FiltersExtension.engines(setup: EnginesExtension.() -> Unit) {
//    when (this) {
//        is ExtensionAware -> extensions.getByType(EnginesExtension::class.java).setup()
//        else -> throw Exception("${this::class} must be an instance of ExtensionAware")
//    }
//}


tasks.getByName("build").dependsOn(packForXcode)