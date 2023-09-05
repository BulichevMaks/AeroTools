package com.formgrav.aerotools.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.databinding.FragmentSpeedBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.viewmodel.AirSpeedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel


class AirSpeedFragment : Fragment() {

    private val vm: AirSpeedViewModel by viewModel()
    private lateinit var binding: FragmentSpeedBinding
    private var startGrayAngle = 40
    private var sweepGrayAngle = 20
    private var startGreenAngle = 60
    private var sweepGreenAngle = 40
    private var startYellowAngle = 100
    private var sweepYellowAngle = 20
    private var startRedAngle = 120
    private var sweepRedAngle = 40
    var speedKmPerHour: Float = 0.0F
    var gpsJob: Job? = null


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
            Log.d("SETT", "$settings")
            if (settings == null) {
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
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(500)
            Log.d("SETT22", "${vm.getSettings()}")
            withContext(Dispatchers.Main) {
                binding.speedView.setStartGrayAngle(startGrayAngle)
                binding.speedView.setSweepGrayAngle(sweepGrayAngle)
                binding.speedView.setStartYellowAngle(startYellowAngle)
                binding.speedView.setSweepYellowAngle(sweepYellowAngle)
                binding.speedView.setStartRedAngle(startRedAngle)
                binding.speedView.setSweepRedAngle(sweepRedAngle)
                binding.speedView.setStartGreenAngle(startGreenAngle)
                binding.speedView.setSweepGreenAngle(sweepGreenAngle)
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
            ))
        }
    }

    companion object {
        fun newInstance() = AirSpeedFragment()
    }
}