plugins {
    `kotlin-dsl`
}

group = "buildlogic"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.6")
    implementation("se.bjurr.violations:violations-gradle-plugin:1.52.7")
}
