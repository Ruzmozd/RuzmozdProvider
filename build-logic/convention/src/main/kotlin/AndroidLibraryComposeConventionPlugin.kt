import com.android.build.gradle.LibraryExtension
import ir.hrkancorp.ruzmozdprovider.configureComposeAndroid
import ir.hrkancorp.ruzmozdprovider.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply {
                plugin("com.android.library")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            extensions.configure<LibraryExtension> {
                defaultConfig { consumerProguardFiles("consumer-rules.pro") }
                configureKotlinAndroid(libs, this)
                configureComposeAndroid(libs)
            }
        }
    }

}