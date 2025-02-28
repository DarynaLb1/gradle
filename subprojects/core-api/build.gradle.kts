plugins {
    id("gradlebuild.distribution.api-java")
}

description = "Public and internal 'core' Gradle APIs that are required by other subprojects"

errorprone {
    disabledChecks.addAll(
        "InlineMeSuggester", 
        "MalformedInlineTag", 
        "MixedMutabilityReturnType", 
        "NonApiType", 
        "ReferenceEquality", 
        "StringCharset"
    )
}

dependencies {
    // API dependencies (exposed to consumers)
    compileOnly(libs.jetbrainsAnnotations)

    api(projects.stdlibJavaExtensions)
    api(projects.buildCacheSpi)
    api(projects.loggingApi)
    api(projects.baseServices)
    api(projects.files)
    api(projects.resources)
    api(projects.persistentCache)
    api(projects.declarativeDslApi)
    api(libs.jsr305)
    api(libs.groovy)
    api(libs.groovyAnt)
    api(libs.guava)
    api(libs.ant)
    api(libs.inject)

    // Implementation dependencies (internal usage)
    implementation(projects.io)
    implementation(projects.baseServicesGroovy)
    implementation(projects.logging)
    implementation(projects.buildProcessServices)
    implementation(libs.commonsLang)
    implementation(libs.slf4jApi)

    // Runtime dependencies
    runtimeOnly(libs.kotlinReflect)

    // Test dependencies
    testImplementation(libs.asm)
    testImplementation(libs.asmCommons)
    testImplementation(testFixtures(projects.core))
    testImplementation(testFixtures(projects.logging))

    // Test fixtures
    testFixturesImplementation(projects.baseServices)

    // Integration test dependencies
    integTestDistributionRuntimeOnly(projects.distributionsBasics)
}

packageCycles {
    excludePatterns.add("org/gradle/**")
}

strictCompile {
    ignoreRawTypes() // Raw types used in public API
}

integTest.usesJavadocCodeSnippets = true
testFilesCleanup.reportOnly = true

tasks.isolatedProjectsIntegTest {
    enabled = false
}
