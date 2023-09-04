package com.formgrav.aerotools.data.repository

import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.domain.api.ArduinoRepository

class ArduinoRepositoryImpl(private val arduinoClient: ArduinoClientImpl): ArduinoRepository {

    override fun startAltFlow() {
        arduinoClient.startAltFlow()
    }

    override fun stopAltFlow() {
        arduinoClient.stopAltFlow()
    }

    override fun setNewPressure(pressure: String) {
        arduinoClient.setNewPressure(pressure)
    }
}