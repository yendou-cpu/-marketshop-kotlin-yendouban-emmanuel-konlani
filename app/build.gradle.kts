plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

    id("com.google.devtools.ksp")
}
android {
    namespace = "com.example.projetgestion1"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.projetgestion1"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner =
            "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile(
                    "proguard-android-optimize.txt"
                ),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.play.services.analytics.impl)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

// Gson Converter
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

// Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

// Room
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

// Coil (images)
    implementation("io.coil-kt:coil-compose:2.7.0")

// Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.0")

// ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

// LiveData / Lifecycle Compose
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    // Material 3
    implementation("androidx.compose.material3:material3")

// Icons
    implementation("androidx.compose.material:material-icons-extended")

// Activity Compose
    implementation("androidx.activity:activity-compose:1.9.2")
}