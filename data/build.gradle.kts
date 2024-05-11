plugins {
    alias(libs.plugins.ruzmozdprovider.android.library)
}

android {
    namespace = "ir.hirkancorp.data"
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.dataStore)
    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.pagingRunTime)
    implementation(project(":core"))
    implementation(project(":domain"))
}