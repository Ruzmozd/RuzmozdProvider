import com.android.build.api.dsl.ApplicationExtension
import ir.hrkancorp.ruzmozdprovider.configureComposeAndroid
import ir.hrkancorp.ruzmozdprovider.configureKotlinAndroid
import ir.hrkancorp.ruzmozdprovider.implementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("com.android.application")
                plugin("org.jetbrains.kotlin.android")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables {
                        useSupportLibrary = true
                    }
                }
                configureKotlinAndroid(libs, this)
                configureComposeAndroid(libs,this)
            }


            dependencies {
                implementation(libs.findBundle("androidX").get())
                implementation(libs.findBundle("splashApi").get())
                implementation(libs.findBundle("kotlinCoroutines").get())
                implementation(libs.findBundle("koin").get())
                implementation(libs.findBundle("coil").get())
                implementation(libs.findBundle("test").get())
            }
        }
    }
}