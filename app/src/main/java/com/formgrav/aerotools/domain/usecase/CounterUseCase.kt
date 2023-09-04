package com.formgrav.aerotools.domain.usecase

import com.formgrav.aerotools.domain.api.ArduinoRepository

class CounterUseCase(private val arduinoRepository: ArduinoRepository) {

    fun startAltFlow() {
        arduinoRepository.startAltFlow()
    }

    fun stopAltFlow() {
        arduinoRepository.stopAltFlow()
    }

    fun setNewPressure(pressure: String) {
        arduinoRepository.setNewPressure(pressure)
    }
}