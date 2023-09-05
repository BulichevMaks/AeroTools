package com.formgrav.aerotools.domain.usecase

import com.formgrav.aerotools.domain.api.SettingsRepository
import com.formgrav.aerotools.domain.model.Settings

class CRUDSettingsUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun getSettings(): Settings? {
        return settingsRepository.getSettings()
    }

    suspend fun saveSettings(settings: Settings) {
        settingsRepository.saveSettings(settings = settings)
    }

    suspend fun insertSettings(settings: Settings) {
        settingsRepository.insertSettings(settings = settings)
    }
}