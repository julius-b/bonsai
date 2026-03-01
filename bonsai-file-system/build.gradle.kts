import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("com.android.library")
    id("com.vanniktech.maven.publish")
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm()

    sourceSets {
        commonMain {
            dependencies {
                api(projects.bonsaiCore)
                api(libs.okio)
                compileOnly(libs.compose.foundation)
                compileOnly(libs.compose.ui)
                implementation(libs.compose.components.resources)
            }
        }
    }
}

android {
    namespace = "cafe.adriel.bonsai.filesystem"
    compileSdk = 36
}
