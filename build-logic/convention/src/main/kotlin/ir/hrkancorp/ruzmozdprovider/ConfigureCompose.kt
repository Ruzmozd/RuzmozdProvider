package ir.hrkancorp.ruzmozdprovider

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies

fun Project.configureComposeAndroid(
    libs: VersionCatalog,
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
        }
    }

    dependencies {
        add("implementation", libs.findBundle("compose").get())
    }
}