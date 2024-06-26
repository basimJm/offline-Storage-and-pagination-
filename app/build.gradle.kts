plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.blackhand.trainingapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.blackhand.trainingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //dagger hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kapt ("com.google.dagger:hilt-compiler:2.50")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")


    //fragment
    implementation ("androidx.fragment:fragment-ktx:1.5.7")
    implementation ("androidx.fragment:fragment:1.5.7")
    implementation ("androidx.fragment:fragment-ktx:1.5.7")

    //viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")

    //Room components
//    implementation ("androidx.room:room-runtime:2.4.3")
//    kapt ("androidx.room:room-compiler:2.4.3")

    //Extensions and coroutines for room
    implementation ("androidx.room:room-ktx:2.4.3")

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")


    //paging3
    implementation("androidx.paging:paging-runtime:3.2.1")

    //coil
    implementation("io.coil-kt:coil:2.6.0")
}
kapt {
    correctErrorTypes = true
    useBuildCache = true
}
