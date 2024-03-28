plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

rootProject.extra.apply {
    set("PUBLISH_ARTIFACT_ID", "qrcode")
}

apply(from = "$rootDir/scripts/publish.gradle")

android {
    namespace = "com.tomorrow.qrcode"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
}

dependencies {

    implementation(libraries.compose.ui)
    implementation(libraries.compose.ui.graphics)
    implementation(libraries.compose.material3)
    //qrcode encoding
    implementation( "com.google.zxing:core:3.5.1")
}