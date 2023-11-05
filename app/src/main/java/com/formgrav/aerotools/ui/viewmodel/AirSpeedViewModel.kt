package com.formgrav.aerotools.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.formgrav.aerotools.domain.model.AttitudeData
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.domain.usecase.CRUDSettingsUseCase
import kotlinx.coroutines.Job

class AirSpeedViewModel(private val crudSettingsUseCase: CRUDSettingsUseCase): ViewModel() {

    private val _airSpeedLiveData = MutableLiveData<AttitudeData>()
    val settingsLiveData: LiveData<AttitudeData> = _airSpeedLiveData


    public override fun onCleared() {
        super.onCleared()
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