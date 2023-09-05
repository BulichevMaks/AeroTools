package com.formgrav.aerotools.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.domain.usecase.CRUDSettingsUseCase

class RedLineViewModel(private val crudSettingsUseCase: CRUDSettingsUseCase): ViewModel() {

    public override fun onCleared() {
        super.onCleared()
    }

    suspend fun getSettings(): Settings? {
        return crudSettingsUseCase.getSettings()
    }
}