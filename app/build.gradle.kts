plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"
}

android {
    namespace = "com.example.alarmgroups"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.girish.alarmgroups"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf("-Xjvm-default=all")

    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    val composeUiVersion = "1.1.1"
    implementation(libs.androidx.core.ktx)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:$composeUiVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")
    implementation("androidx.compose.material:material:1.1.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeUiVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeUiVersion")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.5.0")

    // Room
    implementation("androidx.room:room-runtime:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-compiler:2.44.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")

    // Navigation in compose
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.28.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-swiperefresh:0.28.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    // Wheel Picker
    implementation("com.github.gargVader:WheelPickerCompose:Release")
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.1.6")

    // Moshi - Type Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Leak Canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.10")

}