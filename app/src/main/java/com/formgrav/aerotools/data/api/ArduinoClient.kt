package com.formgrav.aerotools.data.api

interface ArduinoClient {
     fun startAltFlow()
     fun stopAltFlow()
     fun setNewPressure(p: String)
}