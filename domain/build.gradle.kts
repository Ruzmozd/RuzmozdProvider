plugins {
    alias(libs.plugins.ruzmozdprovider.android.library)
}

android {
    namespace = "ir.hirkancorp.domain"
}

dependencies {
    implementation(libs.bundles.androidX)
    implementation(libs.bundles.kotlinCoroutines)
    implementation(libs.bundles.koin)
    implementation(libs.bundles.pagingRunTime)

    implementation(project(":core"))
}