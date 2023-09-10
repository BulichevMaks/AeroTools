package com.formgrav.aerotools.data.datasourse.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings_table")
data class SettingsEntity(
    @PrimaryKey
    val id: Int,
    val startGrayAngle: Int?,
    val sweepGrayAngle: Int?,
    val startGreenAngle: Int?,
    val sweepGreenAngle: Int?,
    val startYellowAngle: Int?,
    val sweepYellowAngle: Int?,
    val startRedAngle: Int?,
    val sweepRedAngle: Int?,
    val startRedAngle2: Int?,
    val sweepRedAngle2: Int?,
)
