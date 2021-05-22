package com.example.intervaltrainingtimer

import android.app.*
import dagger.hilt.android.*
import timber.log.*

@HiltAndroidApp
class IntervalTimerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}