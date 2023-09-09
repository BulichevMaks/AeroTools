package com.formgrav.aerotools.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

import com.formgrav.aerotools.R
import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.databinding.FragmentVarioBinding
import com.formgrav.aerotools.ui.customview.ButtonDrawable
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin


class VarioFragment : Fragment() {
    private lateinit var binding: FragmentVarioBinding
    private lateinit var arduinoRepositoryImpl: ArduinoClientImpl
    private var alt = "0"
    private var currentPressure = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVarioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arduinoRepositoryImpl = getKoin().get<ArduinoClientImpl>()

        val toneGen = ToneGenerator(AudioManager.STREAM_SYSTEM, ToneGenerator.MAX_VOLUME)

        (arduinoRepositoryImpl as ArduinoClientImpl).counLiveData.observe(viewLifecycleOwner) { data ->
            requireActivity().runOnUiThread {
                if (data.altitude.isNotEmpty()) {
                    alt = data.altitude
                    var formattedString = data.altitude.substring(0, data.altitude.length - 1)
                    if (formattedString.isEmpty() || formattedString == "-") {
                        formattedString = "0"
                    }
                    binding.altitude.text = formattedString
                }
                if (data.pressure != "" && !data.pressure.contains("_")) {
                    currentPressure = data.pressure
                }
            }
        }
        binding.progressBar.max = 100
        binding.progressBar.progress = 0
        val buttonDrawable = ButtonDrawable()
        binding.zeroButton.background = buttonDrawable
        binding.zeroButton.setOnClickListener {
            buttonDrawable.setPressed(true)
            if (arduinoRepositoryImpl.isSerialConnectionOpen()) {
                arduinoRepositoryImpl.setNewPressure("pre")

                binding.progressBar.visibility = View.VISIBLE

                lifecycleScope.launch(Dispatchers.IO) {
                    var progress = 0
                    while (progress < 101) {
                        delay(25)
                        progress++
                        withContext(Dispatchers.Main) {
                            binding.progressBar.progress = progress
                            if (progress >= 100) {
                                binding.progressBar.visibility = View.GONE
                                binding.currentPressure.text =
                                    "CP-$currentPressure"
                            }
                        }
                    }
                    buttonDrawable.setPressed(false)
                    arduinoRepositoryImpl.setNewPressure("set$currentPressure")
                }
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Sensors are not connected")
                    .setNeutralButton("ОК"
                    ) { dialog, which ->

                    }
                    .show()
            }
        }
        val buttonDrawable1 = ButtonDrawable()
        buttonDrawable1.text = "SET"
        buttonDrawable1.outerColor = Color.GRAY
        binding.setButton.background = buttonDrawable1
        binding.setButton.setOnClickListener {
            val newPressure = binding.editPressureView.text.toString()
            if (newPressure != "") {
                if (arduinoRepositoryImpl.isSerialConnectionOpen()) {


                    arduinoRepositoryImpl.setNewPressure("set$newPressure")
                }
                binding.currentPressure.text =
                    "CP-$newPressure"
                binding.editPressureView.setText("")
            } else {
                MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Sensors are not connected")
                    .setNeutralButton("ОК"
                    ) { dialog, which ->

                    }
                    .show()
            }
        }

        if (arduinoRepositoryImpl.isSerialConnectionOpen()) {

            lifecycleScope.launch(Dispatchers.IO) {
                delay(3000)
                var verticalSpeed = 0.0
                var firstAlt = alt.toInt()
                var prevTimestamp = System.currentTimeMillis()
                while (arduinoRepositoryImpl.isSerialConnectionOpen()) {

                    delay(1000)
                    val secondAlt = alt.toInt()
                    val currentTimestamp = System.currentTimeMillis()

                    val altitudeChangePerSecond = (secondAlt - firstAlt)

                    val deltaTime = (currentTimestamp - prevTimestamp) / 1000.0
                    val altitudeChange = (secondAlt - firstAlt).toDouble()
                    if (altitudeChange >= 0) {
                        verticalSpeed = ((altitudeChange / deltaTime) + 0.1).toInt().toDouble() / 10
                    } else {
                        verticalSpeed = ((altitudeChange / deltaTime) - 0.1).toInt().toDouble() / 10
                    }

                    withContext(Dispatchers.Main) {

                        when (altitudeChangePerSecond) {
                            in -2..2 -> {
                                binding.verticalSpeedView.text = "0"
                                binding.verticalSpeedView.setTextColor(Color.WHITE)
                                binding.arrowView.setArrowRotationAnimated(0)
                            }
                            in 3..99 -> {
                                binding.arrowView.setArrowRotationAnimated(altitudeChangePerSecond)
                                toneGen.startTone(ToneGenerator.TONE_PROP_BEEP, 500)
                                binding.verticalSpeedView.setTextColor(Color.GREEN)
                                binding.verticalSpeedView.text = "$verticalSpeed"
                            }
                            in 100..200 -> {
                                binding.arrowView.setArrowRotationAnimated(altitudeChangePerSecond)
                                binding.verticalSpeedView.setTextColor(Color.GREEN)
                                binding.verticalSpeedView.text = "$verticalSpeed"
                            }
                            in -3 downTo -99 -> {
                                binding.arrowView.setArrowRotationAnimated(altitudeChangePerSecond)
                                binding.verticalSpeedView.setTextColor(Color.GRAY)
                                binding.verticalSpeedView.text = "$verticalSpeed"
                            }
                            in -100 downTo -200 -> {
                                binding.arrowView.setArrowRotationAnimated(altitudeChangePerSecond)
                                binding.verticalSpeedView.setTextColor(Color.GRAY)
                                binding.verticalSpeedView.text = "$verticalSpeed"
                            }
                            else -> {}
                        }
                        lifecycleScope.launch(Dispatchers.IO) {
                            when (altitudeChangePerSecond) {
                                in 5..9 -> {
                                    toneGen.startTone(
                                        ToneGenerator.TONE_PROP_BEEP,
                                        altitudeChangePerSecond
                                    )
                                }
                                in 10..19 -> {
                                    for (i in 1..5) {
                                        toneGen.startTone(
                                            ToneGenerator.TONE_PROP_BEEP,
                                            altitudeChangePerSecond
                                        )
                                        delay(250)
                                    }
                                }
                                in 20..29 -> {
                                    for (i in 1..20) {
                                        toneGen.startTone(
                                            ToneGenerator.TONE_PROP_BEEP,
                                            altitudeChangePerSecond
                                        )
                                        delay(50)
                                    }
                                }
                                in 30..99 -> {
                                    for (i in 1..40) {
                                        toneGen.startTone(
                                            ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD,
                                            altitudeChangePerSecond
                                        )
                                        delay(25)
                                    }
                                }
                                in -5 downTo -99 -> {
                                    toneGen.startTone(ToneGenerator.TONE_CDMA_CALLDROP_LITE, 2000)
                                }

                                else -> {}
                            }
                        }
                    }
                    firstAlt = secondAlt
                    prevTimestamp = currentTimestamp
                }
            }
        }

        // Ограничение максимальной длины всего ввода
        val maxLength = 7
        binding.editPressureView.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
        // Ограничение максимального числа знаков после запятой
        val decimalDigits = 2
        binding.editPressureView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                val decimalSeparator = ","

                if (text.contains(decimalSeparator)) {
                    val indexOfSeparator = text.indexOf(decimalSeparator)
                    if (text.length - indexOfSeparator - 1 > decimalDigits) {
                        s?.delete(indexOfSeparator + decimalDigits + 1, text.length)
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    companion object {
        fun newInstance() = VarioFragment()
    }
}