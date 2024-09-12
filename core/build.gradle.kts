import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerializer)
    alias(libs.plugins.kotlinAndroidParcelize)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
            freeCompilerArgs.addAll(
                "-P",
                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=org.example.project.Parcelize"
            )
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
            api(libs.androidx.lifecycle.viewmodel)
            api(libs.androidx.lifecycle.runtime.compose)

            api(libs.slf4j.simple)

            api(libs.kotlinx.coroutines.core)
            api(libs.ktor.client.core)
            api(libs.ktor.client.logging)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.serialization.kotlinx.json)

            api(libs.image.loader)

            api(libs.multiplatform.settings)
            api(libs.multiplatform.settings.no.arg)
        }

        androidMain.dependencies {
            api(compose.preview)
            api(libs.androidx.activity.compose)

            api(libs.kotlinx.coroutines.android)
            api(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.cannadys.core"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}