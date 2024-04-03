import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    base
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.6")
}

val configFile = file("$rootDir/detekt-config.yml")
val baselineFile = file("detekt-baseline.xml")
val mergedReportFile = file("${rootProject.layout.buildDirectory}/reports/detekt/report.sarif")
val outputReportFile = file("${layout.buildDirectory}/reports/detekt/detekt.sarif")

detekt {
    config.setFrom(configFile)
    buildUponDefaultConfig = true
    ignoreFailures = false
    parallel = true
    baseline = baselineFile
    basePath = rootDir.absolutePath
}

val reportMerge = tasks.register<ReportMergeTask>("reportMerge") {
    description = "Runs merge of all detekt reports into single one"
    output.set(mergedReportFile)
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(false)
        md.required.set(false)
        sarif.required.set(true)
        txt.required.set(false)
        xml.required.set(false)
        sarif.outputLocation.set(outputReportFile)
    }
}

plugins.withType<DetektPlugin> {
    tasks.withType<Detekt> {
        finalizedBy(reportMerge)
        reportMerge.configure {
            input.from(sarifReportFile)
        }
        finalizedBy()
    }
}
