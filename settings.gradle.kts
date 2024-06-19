rootProject.name = "dark-common"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        maven("https://darkmeowteam.github.io/maven/")
    }

    val kotlinVersion: String by settings

    plugins {
        id("org.jetbrains.kotlin.jvm").version(kotlinVersion)
    }
}