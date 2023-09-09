package com.formgrav.aerotools.ui.fragment

import com.formgrav.aerotools.R
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.formgrav.aerotools.databinding.FragmentMapBinding
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.rootfragments.DownRootFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var locationManager: LocationManager
    private lateinit var mapview: MapView
    private var isMapSwipeEnabled = false
    var latitude = 0.0
    var longitude = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeLocationManager()
        MapKitFactory.setApiKey("5d313c7e-e1d5-405d-89f1-df4d4a65dde2")
        MapKitFactory.initialize(requireContext())
        arguments?.let {
            isMapSwipeEnabled = it.getBoolean(ARG_MAP_SWIPE_ENABLED, true)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mapview = binding.mapview

        // Установите начальную позицию и отобразите карту
        mapview.map.move(
            CameraPosition(Point(44.8059375, 20.45707615), 16.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 4F),
            null
        )
        val mapKit: MapKit = MapKitFactory.getInstance()
        val loc = mapKit.createUserLocationLayer(mapview.mapWindow)
        loc.isVisible = true

        binding.imageView.setOnClickListener {

            if (activity is RootActivity) {
                ((activity as RootActivity).findDownRootFragment() as DownRootFragment).viewPager.setCurrentItem(2,false)
            }

        }

    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapview.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }


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
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
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
            latitude = location.latitude
            longitude = location.longitude
            Log.d("LOCATION", "latitude: $latitude")
            Log.d("LOCATION", "longitude: $longitude")
            // Дополнительные действия с полученными координатами
            // Получение скорости в м/с
            val speedMetersPerSec = location.speed
            // Преобразование скорости в км/ч
            //  speedKmPerHour = speedMetersPerSec * 3.6f
            // Используйте speedKmPerHour для дальнейших действий
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        private const val ARG_MAP_SWIPE_ENABLED = "map_swipe_enabled"

        fun newInstance(isMapSwipeEnabled: Boolean): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putBoolean(ARG_MAP_SWIPE_ENABLED, isMapSwipeEnabled)
            fragment.arguments = args
            return fragment
        }

        private const val REQUEST_CODE = 1
        private const val MIN_TIME_BETWEEN_UPDATES: Long = 100 // 0.1 секунда
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.1f // 10 метров
    }
}