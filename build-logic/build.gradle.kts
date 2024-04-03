plugins {
    base
    id("io.gitlab.arturbosch.detekt").version("1.23.6")
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")
    }
}