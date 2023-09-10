package com.formgrav.aerotools.domain.model

data class Settings(
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
