package com.formgrav.aerotools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.domain.usecase.CRUDSettingsUseCase
import kotlinx.coroutines.Job

class AirSpeedViewModel(private val crudSettingsUseCase: CRUDSettingsUseCase): ViewModel() {

    private var isCountEnabled = true
    private var timerJob: Job? = null

    private val _settingsLiveData = MutableLiveData<Settings>()
    val settingsLiveData: LiveData<Settings> = _settingsLiveData


    public override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }

    suspend fun getSettings(): Settings? {
        return crudSettingsUseCase.getSettings()
    }
    suspend fun insertSettings(settings: Settings) {
        return crudSettingsUseCase.insertSettings(settings)
    }
    suspend fun saveSettings(settings: Settings) {
        crudSettingsUseCase.saveSettings(settings)
    }
}