import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlinCocoapods)
    id("maven-publish")
}

val libraryVersion: String by project

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = libraryVersion
        ios.deploymentTarget = "15"
        name = "LicenseManager"
        authors = "Avenza System"
        license = "Private"
        source = "{ :git => 'git@github.com:jcenturion18/license_manager.git', :tag => '$libraryVersion' }"

        framework {
            baseName = "LicenseManager"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
    }

    androidTarget {
        publishLibraryVariants("release", "debug")
    }
}

android {
    namespace = "com.avenza.license"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

tasks.register("buildLibrary") {
    group = "Avenza Build"
    dependsOn("clean")
    dependsOn("podPublishXCFramework")
}

tasks.register<Exec>("publishingLibrary") {
    group = "Avenza Publishing"
    dependsOn("buildLibrary")
    dependsOn("publishToMavenLocal")
    commandLine("../scripts/pushios.sh", libraryVersion)
}