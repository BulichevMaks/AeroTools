package com.formgrav.aerotools.data.repository

import com.formgrav.aerotools.data.converters.SettingsDbConvertor
import com.formgrav.aerotools.data.datasourse.room.AppDatabase
import com.formgrav.aerotools.data.datasourse.room.model.SettingsEntity
import com.formgrav.aerotools.domain.api.SettingsRepository
import com.formgrav.aerotools.domain.model.Settings

class SettingsRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val settingsDbConvertor: SettingsDbConvertor,
) : SettingsRepository {

    override suspend fun getSettings(): Settings? {
        return convertFromSettingsEntityToSettings(appDatabase.settingsDao().getSettings())
    }

    override suspend fun saveSettings(settings: Settings) {
        appDatabase.settingsDao().updateSettings(convertFromSettingsToEntity(settings))
    }

    override suspend fun insertSettings(settings: Settings) {
        appDatabase.settingsDao().insertSettings(convertFromSettingsToEntity(settings))
    }

    private fun convertFromSettingsEntityToSettings(settings: SettingsEntity): Settings? {
        return settingsDbConvertor.map(settings)
    }

    private fun convertFromSettingsToEntity(settings: Settings): SettingsEntity {
        return settingsDbConvertor.map(settings)
    }

}