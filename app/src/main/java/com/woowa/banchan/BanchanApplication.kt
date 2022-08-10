package com.woowa.banchan

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BanchanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: BanchanApplication
    }
}