package com.example.intervaltrainingtimer

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.*
import javax.inject.*

@HiltViewModel
class HomeWorkoutViewModel @Inject constructor() : ViewModel() {

    private val setsNumberMutable: MutableLiveData<Int> = MutableLiveData(0)
    val numberOfSets: LiveData<Int> = setsNumberMutable

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
}