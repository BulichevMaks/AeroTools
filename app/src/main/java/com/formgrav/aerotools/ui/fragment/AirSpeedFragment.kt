package com.formgrav.aerotools.ui.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentSpeedBinding
import com.formgrav.aerotools.ui.tabfragments.GrayLineFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin


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


          //  Log.d("NEW_VAL", "tag: $tag")

    }
    fun receiveStartFromSettings(start: Int) {
        // Обработайте полученные данные здесь
        binding.speedView.setStartGrayAngle(start)
    }
    fun receiveEndFromSettings(end: Int) {
        // Обработайте полученные данные здесь
        binding.speedView.setSweepGrayAngle(end)
    }

    companion object {
        fun newInstance() = AirSpeedFragment()
    }
}