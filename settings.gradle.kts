rootProject.name = "dark-common"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
    }

    val kotlinVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm").version(kotlinVersion)
    }
}