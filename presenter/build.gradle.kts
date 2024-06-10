plugins {
    alias(libs.plugins.ruzmozdprovider.android.library.compose)
    alias(libs.plugins.googleServices)
}

android {
    namespace = "ir.hirkancorp.presenter"
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.bundles.coil)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.pagingCompose)
    implementation(libs.bundles.playServicesLocation)
    implementation(libs.bundles.osmAndroid)
    implementation(libs.bundles.firebase)

    implementation(project(":core"))
    implementation(project(":domain"))
}