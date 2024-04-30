import com.android.build.api.dsl.ApplicationExtension
import ir.hrkancorp.ruzmozdprovider.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            applyPlugins()
            setProjectConfig()
        }
    }

    private fun Project.applyPlugins() {
        apply {
            plugin("com.android.application")
            plugin("org.jetbrains.kotlin.android")
        }
    }

    private fun Project.setProjectConfig() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val extension = extensions.getByType<ApplicationExtension>()
        extension.apply {

            compileSdk = libs.findVersion("compileSdk").get().toString().toInt()

            defaultConfig {
                applicationId = "ir.hirkancorp.ruzmozdprovider"
                targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                versionCode = libs.findVersion("versionCode").get().toString().toInt()
                versionName = libs.findVersion("versionName").get().toString()

                vectorDrawables {
                    useSupportLibrary = true
                }
            }

//            compileOptions {
//                sourceCompatibility = JavaVersion.VERSION_1_8
//                targetCompatibility = JavaVersion.VERSION_1_8
//            }
//
//            extensions.configure<KotlinCompile> {
//                kotlinOptions {
//                    jvmTarget = "1.8"
//                }
//            }
            configureComposeAndroid(libs, this)
        }

    }

}