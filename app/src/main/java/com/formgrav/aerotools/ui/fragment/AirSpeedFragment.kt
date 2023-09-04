package com.formgrav.aerotools.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formgrav.aerotools.databinding.FragmentSpeedBinding
import kotlinx.coroutines.Job



class AirSpeedFragment : Fragment() {
    private lateinit var binding: FragmentSpeedBinding
    var speedKmPerHour: Float = 0.0F
    var gpsJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSpeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    fun receiveStartGrayFromSettings(start: Int) {
        binding.speedView.setStartGrayAngle(start)
    }
    fun receiveEndGrayFromSettings(end: Int) {
        binding.speedView.setSweepGrayAngle(end)
    }

    fun receiveStartYellowFromSettings(start: Int) {
        binding.speedView.setStartYellowAngle(start)
    }
    fun receiveEndYellowFromSettings(end: Int) {
        binding.speedView.setSweepYellowAngle(end)
    }
    fun receiveStartRedFromSettings(start: Int) {
        binding.speedView.setStartRedAngle(start)
    }
    fun receiveEndRedFromSettings(end: Int) {
        binding.speedView.setSweepRedAngle(end)
    }
    fun receiveStartGreenFromSettings(start: Int) {
        binding.speedView.setStartGreenAngle(start)
    }
    fun receiveEndGreenFromSettings(end: Int) {
        binding.speedView.setSweepGreenAngle(end)
    }

    companion object {
        fun newInstance() = AirSpeedFragment()
    }
}