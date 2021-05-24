package com.example.intervaltrainingtimer.utils

import androidx.lifecycle.*
import timber.log.*

object LiveDataUtils {

    fun <T1, T2, R> LiveData<T1>.combineWith(
        liveData: LiveData<T2>,
        block: (T1?, T2?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block(this.value, liveData.value)
        }
        result.addSource(liveData) {
            result.value = block(this.value, liveData.value)
        }
        return result
    }

    fun <T1, T2, T3, R> LiveData<T1>.combineWith(
        first: LiveData<T2>,
        second: LiveData<T3>,
        block: (T1?, T2?, T3?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(this) {
            result.value = block(this.value, first.value, second.value)
        }
        result.addSource(first) {
            result.value = block(this.value, first.value, second.value)
        }
        result.addSource(second) {
            result.value = block(this.value, first.value, second.value)
        }
        Timber.d("temp, result: ${result.value}, this.value: ${this.value}, first.value: ${first.value}, second.value: ${second.value}")
        return result
    }

    fun <T1, T2, T3, R> combineValues(
        first: LiveData<T1>,
        second: LiveData<T2>,
        third: LiveData<T3>,
        block: (T1?, T2?, T3?) -> R
    ): LiveData<R> {
        val result = MediatorLiveData<R>()
        result.addSource(first) {
            result.value = block(first.value, second.value, third.value)
        }
        result.addSource(second) {
            result.value = block(first.value, second.value, third.value)
        }
        result.addSource(third) {
            result.value = block(first.value, second.value, third.value)
        }
        return result
    }
}