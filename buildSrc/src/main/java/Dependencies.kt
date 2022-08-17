object Version {
    const val core = "1.8.0"
    const val appcompat = "1.4.2"
    const val constraintLayout = "2.1.4"
    const val fragmentKtx = "1.3.3"
    const val activityKtx = "1.1.0"

    const val googleMaterial = "1.6.1"
    const val recyclerview = "1.3.0-alpha02"
    const val viewModel = "2.2.0"
    const val lifecycleRuntimeKtx = "2.4.1"

    const val hilt = "2.40"

    const val junit4 = "4.13.2"
    const val androidxJunit = "1.1.3"
    const val espressoCore = "3.4.0"

    const val glide = "4.13.2"
    const val glideCompose = "1.6.1"

    const val okhttp = "4.10.0"
    const val moshi = "1.13.0"
    const val retrofit2 = "2.9.0"
    const val coroutine = "1.6.1"
    const val truth = "1.1.2"

    const val compose = "1.4.0"
    const val material = "1.1.1"
    const val composeViewModel = "2.4.1"

    const val room = "2.4.3"
    const val paging = "2.5.0-alpha02"
}

object Lib {

    object Androidx {
        const val core = "androidx.core:core-ktx:${Version.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
        const val activityKtx = "androidx.activity:activity-ktx:${Version.activityKtx}"
        const val googleMaterial = "com.google.android.material:material:${Version.googleMaterial}"

        const val glide = "com.github.bumptech.glide:glide:${Version.glide}"
        const val glideCompiler = "com.github.bumptech.glide:compiler:${Version.glide}"
        const val glideCompose = "com.github.skydoves:landscapist-glide:${Version.glideCompose}"

        const val recyclerview = "androidx.recyclerview:recyclerview:${Version.recyclerview}"
        const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.viewModel}"
        const val lifecycleRuntimeKtx =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}"
    }

    object Hilt {
        const val androidGradlePlugin =
            "com.google.dagger:hilt-android-gradle-plugin:${Version.hilt}"
        const val android = "com.google.dagger:hilt-android:${Version.hilt}"
        const val androidCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
    }

    object Test {
        const val junit4 = "junit:junit:${Version.junit4}"
        const val androidxJunit = "androidx.test.ext:junit:${Version.androidxJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
        const val mockk = "io.mockk:mockk:1.12.0"
        const val truth = "com.google.truth:truth:${Version.truth}"
    }

    object Moshi {
        const val moshi = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
    }

    object OkHttp3 {
        const val core = "com.squareup.okhttp3:okhttp:${Version.okhttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okhttp}"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:4.9.0"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Version.retrofit2}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.retrofit2}"
    }

    object Coroutine {
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutine}"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutine}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Version.coroutine}"
    }

    object Compose {
        const val compose = "androidx.activity:activity-compose:${Version.compose}"
        const val material = "androidx.compose.material:material:${Version.material}"
        const val animation = "androidx.compose.animation:animation:${Version.material}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Version.material}"
        const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.composeViewModel}"
        const val junit4 = "androidx.compose.ui:ui-test-junit4:${Version.material}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${Version.room}"
        const val compiler = "androidx.room:room-compiler:${Version.room}"
        const val roomKtx = "androidx.room:room-ktx:${Version.room}"
        const val test = "androidx.room:room-testing:${Version.room}"
    }
}