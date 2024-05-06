import com.android.build.gradle.LibraryExtension
import ir.hrkancorp.ruzmozdprovider.configureKotlinAndroid
import ir.hrkancorp.ruzmozdprovider.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("com.android.library")
                plugin("org.jetbrains.kotlin.android")
                plugin("kotlin-kapt")
                plugin("kotlinx-serialization")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<LibraryExtension> {
                defaultConfig { consumerProguardFiles("consumer-rules.pro") }
                configureKotlinAndroid(libs, this)
            }

            dependencies {
                implementation(libs.findBundle("test").get())
            }
        }
    }

}