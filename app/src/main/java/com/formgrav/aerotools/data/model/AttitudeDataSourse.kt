package com.formgrav.aerotools.data.model

import com.formgrav.aerotools.domain.model.AttitudeData

data class AttitudeDataSourse(
    val altitude: String,
    val pressure: String,
)
fun AttitudeDataSourse.toDomainModel() = AttitudeData(altitude, pressure)

