package com.formgrav.aerotools.data.model

import com.formgrav.aerotools.domain.model.AttitudeData

data class AttitudeDataSourse(
    val altitude: String,
    val pressure: String,
    val roll: String,
    val pitch: String,
    val airspeed: String,
)
fun AttitudeDataSourse.toDomainModel() = AttitudeData(altitude, pressure, roll, pitch)

