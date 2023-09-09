package com.formgrav.aerotools.ui.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentGpsSpeedBinding
import com.formgrav.aerotools.databinding.FragmentSpeedBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GpsSpeedFragment : Fragment() {
    private lateinit var binding: FragmentGpsSpeedBinding
    private lateinit var locationManager: LocationManager
    var speedKmPerHour: Float = 0.0F
    var gpsJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGpsSpeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeLocationManager()


        gpsJob = lifecycleScope.launch(Dispatchers.IO) {
            while (locationManager.isLocationEnabled) {
                delay(200)
                withContext(Dispatchers.Main) {
                    binding.airspeed.text = "${speedKmPerHour.toInt()}"
                    binding.speedView.setArrowRotationAnimated(speedKmPerHour.toInt())
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        gpsJob?.cancel()
    }

    // Метод для инициализации LocationManager
    private fun initializeLocationManager() {
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES,
            locationListener
        )
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            // Обработка полученных координат
            val latitude = location.latitude
            val longitude = location.longitude
            // Дополнительные действия с полученными координатами
            // Получение скорости в м/с
            val speedMetersPerSec = location.speed
            // Преобразование скорости в км/ч
            speedKmPerHour = speedMetersPerSec * 3.6f
            // Используйте speedKmPerHour для дальнейших действий
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {

        fun newInstance() = GpsSpeedFragment()

        private const val REQUEST_CODE = 1
        private const val MIN_TIME_BETWEEN_UPDATES: Long = 100 // 0.1 секунда
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.1f // 10 метров

    }
}