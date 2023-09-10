package com.formgrav.aerotools.data.datasourse.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE settings_table ADD COLUMN startRedAngle2 INTEGER")
        database.execSQL("ALTER TABLE settings_table ADD COLUMN sweepRedAngle2 INTEGER")
    }
}