package com.formgrav.aerotools.data.datasourse.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.formgrav.aerotools.data.datasourse.room.model.SettingsEntity

@Database(version = 1, entities = [SettingsEntity::class])
abstract class AppDatabase : RoomDatabase(){
    abstract fun settingsDao(): SettingsDao
}