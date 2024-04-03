val mergedReportFile = file("${rootProject.buildDir}/test-results/detekt/report.sarif")

tasks.withType<Test>().configureEach {
    reports.html.required.set(false)
    reports.junitXml.required.set(true)
}
