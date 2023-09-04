package com.formgrav.aerotools.domain.api

interface ArduinoRepository {
     fun startAltFlow()
     fun stopAltFlow()
     fun setNewPressure(pressure: String)
}