package com.formgrav.aerotools.data.datasourse.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.formgrav.aerotools.data.datasourse.room.model.SettingsEntity

@Database(version = 2, entities = [SettingsEntity::class])
abstract class AppDatabase : RoomDatabase(){
    abstract fun settingsDao(): SettingsDao

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return instance as AppDatabase
        }
    }
}

