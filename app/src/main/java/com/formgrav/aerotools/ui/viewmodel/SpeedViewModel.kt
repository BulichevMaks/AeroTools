package com.formgrav.aerotools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.formgrav.aerotools.domain.model.AttitudeData
import com.formgrav.aerotools.domain.usecase.CounterUseCase
import kotlinx.coroutines.Job

class SpeedViewModel(private val counterUseCase: CounterUseCase): ViewModel() {

    private var isCountEnabled = true
    private var timerJob: Job? = null

    private val _counterLiveData = MutableLiveData<AttitudeData>()
    val counterLiveData: LiveData<AttitudeData> = _counterLiveData

    fun counterControl() {
        if(isCountEnabled) {
            counterUseCase.startAltFlow()
            isCountEnabled = false
        } else {
            counterUseCase.stopAltFlow()
            isCountEnabled = true
        }

    }

    fun setNewPressure(pressure: String) {
        counterUseCase.setNewPressure(pressure)
    }

    public override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}