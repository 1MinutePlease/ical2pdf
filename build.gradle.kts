import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "de.paulflohr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)

    // ical4j - Reading the .ics files
    implementation("org.mnode.ical4j:ical4j:3.2.14")

    // File Picker
    implementation("com.darkrockstudios:mpfilepicker:3.1.0")
    testImplementation("junit:junit:4.13.1")

    // Precompose - Navigation, ViewModel
    val precompose_version = "1.5.10"
    api(compose.foundation)
    api(compose.animation)

    api("moe.tlaster:precompose:$precompose_version")
    // api("moe.tlaster:precompose-molecule:$precompose_version") // For Molecule intergration
    api("moe.tlaster:precompose-viewmodel:$precompose_version") // For ViewModel intergration
    api("moe.tlaster:precompose-koin:$precompose_version") // For Koin intergration

    // Koin - Dependency Injection
    implementation("io.insert-koin:koin-core:3.5.3")
    implementation("io.insert-koin:koin-compose:1.1.2")

    // Junit, KoinTest - Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    testImplementation("io.insert-koin:koin-test:3.5.3")

}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ical2pdf"
            packageVersion = "1.0.0"
        }
    }
}
