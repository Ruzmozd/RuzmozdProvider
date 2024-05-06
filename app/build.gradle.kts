plugins {
    alias(libs.plugins.ruzmozdprovider.android.application)
}

android {
    namespace = "ir.hirkancorp.ruzmozdprovider"
    defaultConfig {
        applicationId = "ir.hirkancorp.ruzmozdprovider"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":presenter"))
}