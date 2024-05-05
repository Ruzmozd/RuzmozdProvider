plugins {
    alias(libs.plugins.ruzmozdprovider.android.library)
}

android {
    namespace = "it.hirkancorp.core"
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.koin)
}