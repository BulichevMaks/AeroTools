package com.formgrav.aerotools.domain.api

import com.formgrav.aerotools.domain.model.Settings

interface SettingsRepository {

    suspend fun getSettings(): Settings?

    suspend fun saveSettings(settings: Settings)

    suspend fun insertSettings(settings: Settings)
}