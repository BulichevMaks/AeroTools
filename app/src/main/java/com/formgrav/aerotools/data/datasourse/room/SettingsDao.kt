package com.formgrav.aerotools.data.datasourse.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.formgrav.aerotools.data.datasourse.room.model.SettingsEntity

@Dao
interface SettingsDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSettings(settings: SettingsEntity)

    @Query("SELECT * FROM settings_table")
    suspend fun getSettings(): SettingsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity)

//    @Query("SELECT * FROM settings_table WHERE trackId = :trackId")
//    suspend fun getTrack(trackId: Long): SettingsEntity?
//
//    @Query("DELETE FROM settings_table WHERE trackId = :trackId")
//    suspend fun deleteById(trackId: Long): Int

}