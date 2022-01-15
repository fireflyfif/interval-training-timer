package com.example.intervaltrainingtimer.utils

import android.media.*

object BeepHelper {

    private var toneGenerator: ToneGenerator? = null

    fun shortBeep(duration: Int) {
        if (toneGenerator == null) {
            toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
        }
        toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, duration)
    }

    fun longBeep(duration: Int) {
        if (toneGenerator == null) {
            toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 800)
        }
        toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ONE_MIN_BEEP, duration)
    }

    fun releaseToneGenerator() {
        toneGenerator?.release()
        toneGenerator = null
    }
}