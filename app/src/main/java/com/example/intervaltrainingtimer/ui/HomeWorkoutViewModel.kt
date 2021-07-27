package com.example.intervaltrainingtimer.ui

import androidx.lifecycle.*
import com.example.intervaltrainingtimer.utils.LiveDataUtils.combineValues
import dagger.hilt.android.lifecycle.*
import javax.inject.*

@HiltViewModel
class HomeWorkoutViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val SECONDS_30 = 30000L
        const val SECOND_01 = 1000L
        const val SECONDS_05 = 5000L
    }

    private val setsNumberMutable: MutableLiveData<Int> = MutableLiveData(1)
    val numberOfSets: LiveData<Int> = setsNumberMutable

    private val workoutMillUntilFinishedMutable: MutableLiveData<Long> = MutableLiveData(SECONDS_05) // default value
    val workoutTimeInMillisecond: LiveData<Long> = workoutMillUntilFinishedMutable

    private val restPeriodInMillisecondsMutable: MutableLiveData<Long> = MutableLiveData(SECONDS_05) // default value
    val restPeriodInMilliseconds: LiveData<Long> = restPeriodInMillisecondsMutable

    val combinedData = combineValues(
        setsNumberMutable,
        workoutMillUntilFinishedMutable,
        restPeriodInMillisecondsMutable,
        ::Triple
    ).switchMap { (sets, work, rest) ->
        liveData {
            emit(WorkoutAndRestTime(sets, work, rest))
        }
    }

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
        workoutMillUntilFinishedMutable.value = workoutMillUntilFinishedMutable.value?.minus(SECOND_01)
    }

    fun incrementWorkTime() {
        workoutMillUntilFinishedMutable.value = workoutMillUntilFinishedMutable.value?.plus(SECOND_01)
    }

    fun decrementRestingTime() {
        if (restPeriodInMillisecondsMutable.value == 0L) return
        restPeriodInMillisecondsMutable.value = restPeriodInMillisecondsMutable.value?.minus(SECOND_01)
    }

    fun incrementRestingTime() {
        restPeriodInMillisecondsMutable.value = restPeriodInMillisecondsMutable.value?.plus(SECOND_01)
    }
}

data class WorkoutAndRestTime(val sets: Int?, val workoutTimeInMillis: Long?, val restingTimeInMillis: Long?)