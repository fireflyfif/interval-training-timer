package com.example.intervaltrainingtimer

import androidx.lifecycle.*
import com.kizitonwose.time.*
import dagger.hilt.android.lifecycle.*
import timber.log.*
import java.text.*
import javax.inject.*

@HiltViewModel
class HomeWorkoutViewModel @Inject constructor() : ViewModel() {

    companion object {
        const val SECONDS_30 = 30000L
    }

    private val setsNumberMutable: MutableLiveData<Int> = MutableLiveData(0)
    val numberOfSets: LiveData<Int> = setsNumberMutable

    private val workoutMillUntilFinishedMutable: MutableLiveData<Long> = MutableLiveData(SECONDS_30) // default value
    val workoutTimeInMillisecond: LiveData<Long> = workoutMillUntilFinishedMutable

    private val restPeriodInMillisecondsMutable: MutableLiveData<Long> = MutableLiveData(SECONDS_30) // default value
    val restPeriodInMilliseconds: LiveData<Long> = restPeriodInMillisecondsMutable

    fun decrementNumberOfSets() {
        if (setsNumberMutable.value == 0) return
        setsNumberMutable.value = setsNumberMutable.value?.minus(1)
    }

    fun incrementNumberOfSets() {
        if (setsNumberMutable.value!! > 100) return
        setsNumberMutable.value = setsNumberMutable.value?.plus(1)
    }

    fun setSetsFromUserInput(userValue: String) {
        if (setsNumberMutable.value == userValue.toInt() || userValue.toInt() > 100) return
        setsNumberMutable.value = userValue.toInt()
    }

    fun decrementWorkTime() {
        if (workoutMillUntilFinishedMutable.value == 0L) return
        workoutMillUntilFinishedMutable.value = workoutMillUntilFinishedMutable.value?.minus(1000)
    }

    fun incrementWorkTime() {
        workoutMillUntilFinishedMutable.value = workoutMillUntilFinishedMutable.value?.plus(1000)
    }

    fun decrementRestingTime() {
        if (restPeriodInMillisecondsMutable.value == 0L) return
        restPeriodInMillisecondsMutable.value = restPeriodInMillisecondsMutable.value?.minus(1000)
    }

    fun incrementRestingTime() {
        restPeriodInMillisecondsMutable.value = restPeriodInMillisecondsMutable.value?.plus(1000)
    }

    fun calculateWorkout() {
        val doubleDecimal: NumberFormat = DecimalFormat("00")

        val millisUntilFinished: Long = 1800000 // = 30 min
        val min = millisUntilFinished / 1000 / 60
        val sec = millisUntilFinished / 1000 % 60
        Timber.d("temp,  min: ${doubleDecimal.format(min)}")
        Timber.d("temp,  sec: ${doubleDecimal.format(sec)}")

        val minUntilFinished = 30.minutes
        val secUntilFinished = 30.seconds
        Timber.d("temp,  min: ${doubleDecimal.format(minUntilFinished.longValue)}")
        Timber.d("temp,  sec: ${doubleDecimal.format(secUntilFinished.longValue)}")

        val oneSecond: Interval<Second> = 1.seconds
        val oneMin: Interval<Minute> = 1.minutes
        val duration = 10.minutes + 15.seconds - 3.minutes + 2.hours // Interval<Minute>

        Timber.d("temp, time: ${doubleDecimal.format(oneMin.longValue)} : ${doubleDecimal.format(oneSecond.longValue)}, duration: $duration")

        val minute: Long = 1000 * 60 // 1000 milliseconds = 1 second
        val second: Long = 1000

        //        timerDisplay.text = "${f.format(lapshours)}:${f.format(lapsMin)}:${f.format(lapsSec)}"
        //        hours = totalSecs / 3600;
        //        minutes = (totalSecs % 3600) / 60;
        //        seconds = totalSecs % 60;
        //
        //        timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}