object Version {
    const val core = "1.8.0"
    const val appcompat = "1.4.2"
    const val constraintLayout = "2.1.4"
    const val fragmentKtx = "1.3.3"
    const val googleMaterial = "1.6.1"

    const val junit4 = "4.13.2"
    const val androidxJunit = "1.1.3"
    const val espressoCore = "3.4.0"
}

object Lib {

    object Androidx {
        const val core = "androidx.core:core-ktx:${Version.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Version.constraintLayout}"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:${Version.fragmentKtx}"
        const val googleMaterial = "com.google.android.material:material:${Version.googleMaterial}"
    }

    object Test {
        const val junit4 = "junit:junit:${Version.junit4}"
        const val androidxJunit = "androidx.test.ext:junit:${Version.androidxJunit}"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Version.espressoCore}"
    }
}