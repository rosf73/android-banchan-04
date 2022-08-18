plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        getByName("release") {
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
        viewBinding = true
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.1.1"
    }
}

dependencies {

    implementation(Lib.Androidx.core)
    implementation(Lib.Androidx.appcompat)
    implementation(Lib.Androidx.googleMaterial)
    implementation(Lib.Androidx.constraintLayout)
    implementation(Lib.Androidx.recyclerview)
    implementation(Lib.Androidx.viewModel)
    implementation(Lib.Androidx.fragmentKtx)
    implementation(Lib.Androidx.activityKtx)
    implementation(Lib.Androidx.lifecycleRuntimeKtx)
    testImplementation(Lib.Test.junit4)
    androidTestImplementation(Lib.Test.androidxJunit)
    androidTestImplementation(Lib.Test.espressoCore)
    androidTestImplementation(Lib.Test.truth)
    testImplementation(Lib.OkHttp3.mockWebServer)
    testImplementation(Lib.Test.mockk)
    testImplementation(Lib.Test.truth)

    implementation(Lib.Androidx.glide)
    kapt(Lib.Androidx.glideCompiler)
    implementation(Lib.Androidx.glideCompose)

    implementation(Lib.Hilt.android)
    kapt(Lib.Hilt.androidCompiler)

    implementation(Lib.Coroutine.android)
    implementation(Lib.Coroutine.core)
    implementation(Lib.Coroutine.test)

    implementation(Lib.Moshi.moshi)
    implementation(Lib.OkHttp3.core)
    implementation(Lib.OkHttp3.loggingInterceptor)

    implementation(Lib.Retrofit.core)
    implementation(Lib.Retrofit.moshiConverter)

    implementation(Lib.Compose.compose)
    implementation(Lib.Compose.material)
    implementation(Lib.Compose.animation)
    implementation(Lib.Compose.viewModel)
    implementation(Lib.Compose.tooling)
    androidTestImplementation(Lib.Compose.junit4)

    implementation(Lib.Room.runtime)
    kapt(Lib.Room.compiler)
    implementation(Lib.Room.roomKtx)
    testImplementation(Lib.Room.test)
}