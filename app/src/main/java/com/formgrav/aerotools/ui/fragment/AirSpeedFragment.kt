package com.formgrav.aerotools.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.databinding.FragmentSpeedBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.viewmodel.AirSpeedViewModel
import kotlin.math.sqrt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel


class AirSpeedFragment : Fragment() {

    private val vm: AirSpeedViewModel by viewModel()
    private lateinit var binding: FragmentSpeedBinding
    private lateinit var arduinoRepositoryImpl: ArduinoClientImpl
    private var startGrayAngle = 40
    private var sweepGrayAngle = 20
    private var startGreenAngle = 60
    private var sweepGreenAngle = 40
    private var startYellowAngle = 100
    private var sweepYellowAngle = 20
    private var startRedAngle = 120
    private var sweepRedAngle = 40
    private var startRedAngle2 = 120
    private var sweepRedAngle2 = 40
    var speedKmPerHour: String = "0"
    var airspeedJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var settings: Settings?
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()
            Log.d("SETTINGSDATA","settings $settings")
            Log.d("SETTINGSDATA","startRedAngle2 ${settings?.startRedAngle2}")
            if (settings == null || settings?.startRedAngle2 == null || settings?.sweepRedAngle2 == null) {
                vm.insertSettings(Settings(
                    id = 1,
                    startGrayAngle = 40,
                    sweepGrayAngle = 20,
                    startGreenAngle = 60,
                    sweepGreenAngle = 40,
                    startYellowAngle = 100,
                    sweepYellowAngle = 20,
                    startRedAngle = 120,
                    sweepRedAngle = 40,
                    startRedAngle2 = 0,
                    sweepRedAngle2 = 10,
                ))
            } else {
                 startGrayAngle = settings!!.startGrayAngle!!
                 sweepGrayAngle = settings!!.sweepGrayAngle!!
                 startGreenAngle = settings!!.startGreenAngle!!
                 sweepGreenAngle = settings!!.sweepGreenAngle!!
                 startYellowAngle = settings!!.startYellowAngle!!
                 sweepYellowAngle = settings!!.sweepYellowAngle!!
                 startRedAngle = settings!!.startRedAngle!!
                 sweepRedAngle = settings!!.sweepRedAngle!!
                startRedAngle2 = settings!!.startRedAngle2!!
                sweepRedAngle2 = settings!!.sweepRedAngle2!!
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(500)
            withContext(Dispatchers.Main) {
                binding.speedView.setStartGrayAngle(startGrayAngle)
                binding.speedView.setSweepGrayAngle(sweepGrayAngle)
                binding.speedView.setStartYellowAngle(startYellowAngle)
                binding.speedView.setSweepYellowAngle(sweepYellowAngle)
                binding.speedView.setStartRedAngle(startRedAngle)
                binding.speedView.setSweepRedAngle(sweepRedAngle)
                binding.speedView.setStartRedAngle2(startRedAngle2)
                binding.speedView.setSweepRedAngle2(sweepRedAngle2)
                binding.speedView.setStartGreenAngle(startGreenAngle)
                binding.speedView.setSweepGreenAngle(sweepGreenAngle)

            }
        }
        arduinoRepositoryImpl = getKoin().get<ArduinoClientImpl>()
        (arduinoRepositoryImpl as ArduinoClientImpl).counLiveData.observe(viewLifecycleOwner) { data ->
            requireActivity().runOnUiThread {
                if (data.airspeed.isNotEmpty() && data.airspeed != "nan") {
                    speedKmPerHour = data.airspeed
                }
            }
        }
        airspeedJob = lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                delay(200)
                withContext(Dispatchers.Main) {
                    val number = speedKmPerHour.replace(',', '.').toDouble().toInt()
                    binding.airspeed.text = number.toString()
                    binding.speedView.setArrowRotationAnimated(number)

                }
            }
        }
    }

    fun receiveStartGrayFromSettings(start: Int) {
        binding.speedView.setStartGrayAngle(start)
        startGrayAngle = start
    }

    fun receiveEndGrayFromSettings(end: Int) {
        binding.speedView.setSweepGrayAngle(end)
        sweepGrayAngle = end
    }

    fun receiveStartYellowFromSettings(start: Int) {
        binding.speedView.setStartYellowAngle(start)
        startYellowAngle = start
    }

    fun receiveEndYellowFromSettings(end: Int) {
        binding.speedView.setSweepYellowAngle(end)
        sweepYellowAngle = end
    }

    fun receiveStartRedFromSettings(start: Int) {
        binding.speedView.setStartRedAngle(start)
        startRedAngle = start
    }

    fun receiveEndRedFromSettings(end: Int) {
        binding.speedView.setSweepRedAngle(end)
        sweepRedAngle = end
    }
    fun receiveStartRed2FromSettings(start: Int) {
        binding.speedView.setStartRedAngle2(start)
        startRedAngle2 = start
    }

    fun receiveEndRed2FromSettings(end: Int) {
        binding.speedView.setSweepRedAngle2(end)
        sweepRedAngle2 = end
    }

    fun receiveStartGreenFromSettings(start: Int) {
        binding.speedView.setStartGreenAngle(start)
        startGreenAngle = start
    }

    fun receiveEndGreenFromSettings(end: Int) {
        binding.speedView.setSweepGreenAngle(end)
        sweepGreenAngle = end
    }

    fun saveSettings() {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(100)
            vm.saveSettings(Settings(
                id = 1,
                startGrayAngle,
                sweepGrayAngle,
                startGreenAngle,
                sweepGreenAngle,
                startYellowAngle,
                sweepYellowAngle,
                startRedAngle,
                sweepRedAngle,
                startRedAngle2,
                sweepRedAngle2,
            ))
        }
    }

    override fun onStop() {
        super.onStop()
        airspeedJob?.cancel()
    }

    companion object {
        fun newInstance() = AirSpeedFragment()
    }
}