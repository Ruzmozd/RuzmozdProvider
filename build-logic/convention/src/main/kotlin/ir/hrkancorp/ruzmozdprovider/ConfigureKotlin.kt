package ir.hrkancorp.ruzmozdprovider

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.provideDelegate

internal fun Project.configureKotlinAndroid(
    libs: VersionCatalog,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {

    with(commonExtension) {
        compileSdk = Integer.parseInt(libs.findVersion("compileSdk").get().toString())
        defaultConfig {
            minSdk = Integer.parseInt(libs.findVersion("minSdk").get().toString())
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }




        kotlinOptions {
            val warningsAsErrors: String? by project
            allWarningsAsErrors = warningsAsErrors.toBoolean()
            jvmTarget = JavaVersion.VERSION_17.toString()
        }

    }
/*
    with(android()) {
        buildTypes {
            release {
                isMinifyEnabled = false
                productionVariables()
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            debug {
                developmentVariables()
            }

            staging {
                stagingVariables()
            }
        }
    }*/
}