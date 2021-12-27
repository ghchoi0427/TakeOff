package com.choi.takeoff.ui.mood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MoodViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val progress: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun setProgress(value: Int?) {
        progress.value = value
    }
}