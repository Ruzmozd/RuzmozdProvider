import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ir.hirkancorp.ruzmozdprovider.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "ruzmozdprovider.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}