import se.bjurr.violations.gradle.plugin.ViolationsTask

plugins {
    id("se.bjurr.violations.violations-gradle-plugin")
}

val outputReportFile = file("${layout.buildDirectory}/reports/detekt/code-climate.json")

tasks.register<ViolationsTask>("violations") {
    maxViolations = 0
    doFirst {
        outputReportFile.parentFile.mkdirs()
    }
    codeClimateFile = outputReportFile
    violations = listOf(
        listOf("SARIF", layout.buildDirectory.get().asFile.path, ".*/reports/detekt/report.sarif", "sarif"),
    )
}
