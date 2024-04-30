package ir.hrkancorp.ruzmozdprovider

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.dependencies

fun Project.configureComposeAndroid(
    libs: VersionCatalog,
    commonExtension: CommonExtension<*, *,*,*,*,*>
) {
    commonExtension.apply {
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
        }

        dependencies {
            add("implementation", libs.findLibrary("androidx.core.ktx").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.runtime").get())
            add("implementation", libs.findLibrary("androidx.activity.compose").get())
            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findLibrary("androidx.ui").get())
            add("implementation", libs.findLibrary("androidx.ui.graphics").get())
            add("implementation", libs.findLibrary("androidx.ui.tooling.preview").get())
            add("implementation", libs.findLibrary("androidx.material3").get())
            add("testImplementation", libs.findLibrary("junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
            add("androidTestImplementation", libs.findLibrary("androidx.espresso.core").get())
            add("androidTestImplementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("androidTestImplementation", libs.findLibrary("androidx.ui.test.junit4").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.tooling").get())
            add("debugImplementation", libs.findLibrary("androidx.ui.test.manifest").get())
        }

    }
}