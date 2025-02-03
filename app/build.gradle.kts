plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.voltmeter"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.voltmeter"
        minSdk = 25
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
}

dependencies {


    implementation(libs.appcompat)
    implementation(libs.viewpager2)
    val fragment_version = "1.6.1"
    implementation(libs.fragment)
    implementation(libs.fragment.ktx)
    implementation(libs.fragment.compose)
    debugImplementation(libs.fragment.testing)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}