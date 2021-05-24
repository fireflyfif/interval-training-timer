package com.example.intervaltrainingtimer.utils

import java.text.*

object TimeUtils {

    fun Long.toMin(): Long {
        return this / 1000 / 60
    }

    fun Long.toSec(): Long {
        return this / 1000 % 60
    }

    fun Long.toDecimalString(): String {
        val doubleDecimal: NumberFormat = DecimalFormat("00")
        return doubleDecimal.format(this).toString()
    }
}