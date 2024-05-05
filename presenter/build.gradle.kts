plugins {
    alias(libs.plugins.ruzmozdprovider.android.library)
    alias(libs.plugins.ruzmozdprovider.android.library.compose)
}

android {
    namespace = "it.hirkancorp.presenter"
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.pagingCompose)
    implementation(libs.bundles.playServicesLocation)
    implementation(libs.bundles.osmAndroid)

    implementation(project(":core"))
    implementation(project(":domain"))
}