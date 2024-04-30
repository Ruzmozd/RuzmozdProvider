import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "ir.hirkancorp.ruzmozdprovider.buildlogic"

//java {
//    sourceCompatibility = JavaVersion.VERSION_17
//    targetCompatibility = JavaVersion.VERSION_17
//}
//tasks.withType<KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_17.toString()
//    }
//}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
//        register("applicationComposeConventionPlugin") {
//            id = "ruzmozdprovider.android.compose.application"
//            implementationClass = "ApplicationComposeConventionPlugin"
//        }
        register("androidApplication") {
            id = "ruzmozdprovider.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
//        register("androidApplicationConventionPlugin") {
//            id = "ruzmozdprovider.android.library"
//            implementationClass = "AndroidLibraryConventionPlugin"
//        }
    }
}