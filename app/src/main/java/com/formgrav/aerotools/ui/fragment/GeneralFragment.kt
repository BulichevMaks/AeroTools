package com.formgrav.aerotools.ui.fragment

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.databinding.FragmentGeneralBinding
import com.formgrav.aerotools.domain.model.AttitudeData
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.adapters.AltAdapter
import com.formgrav.aerotools.ui.adapters.SpeedAdapter
import com.formgrav.aerotools.ui.adapters.SpeedColorAdapter
import com.formgrav.aerotools.ui.viewmodel.AirSpeedViewModel
import java.util.ArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneralFragment : Fragment() {
    private lateinit var binding: FragmentGeneralBinding
    private val vm: AirSpeedViewModel by viewModel()
    private lateinit var arduinoRepositoryImpl: ArduinoClientImpl
    private lateinit var locationManager: LocationManager

    var speedKmPerHour: Float = 0.0F
    var gpsJob: Job? = null
    private var alt = "0"
    private var currentPressure = ""
    private var altituds: ArrayList<AttitudeData> = arrayListOf()
    private var speed: ArrayList<String> = arrayListOf()
    private var speedColors: ArrayList<String> = arrayListOf()
    private var altAdapter = AltAdapter(altituds)
    private var speedAdapter = SpeedAdapter(speed)
    private var speedColorAdapter = SpeedColorAdapter(speedColors)
    private var acsel = FloatArray(3)
    private var giro = FloatArray(3)
    private var gravity = FloatArray(9)
    private var magnetic = FloatArray(9)
    private var valuesOrientation = FloatArray(3)
    var degreeX = 0f
    var degreeY = 0f
    private var startGrayAngle = 0
    private var sweepGrayAngle = 10
    private var startGreenAngle = 10
    private var sweepGreenAngle = 5
    private var startYellowAngle = 15
    private var sweepYellowAngle = 20
    private var startRedAngle = 120
    private var sweepRedAngle = 40
    private var startRedAngle2 = 120
    private var sweepRedAngle2 = 40


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var startValue = -250
        var endValue = 10350
        var step = 10

        for (value in startValue..endValue step step) {
            val attitudeData = AttitudeData(value.toString(), "0")
            altituds.add(attitudeData)
        }
        startValue = -20
        endValue = 250
        step = 10

        for (value in startValue..endValue step step) {
            val speedData = value.toString()
            speed.add(speedData)
        }
        startValue = -200
        endValue = 2500
        step = 1

        for (value in startValue..endValue step step) {
            val speedData = value.toString()
            speedColors.add(speedData)
        }
        var settings: Settings?
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()
            if (settings == null) {
                vm.insertSettings(
                    Settings(
                        id = 1,
                        startGrayAngle = 0,
                        sweepGrayAngle = 60,
                        startGreenAngle = 60,
                        sweepGreenAngle = 40,
                        startYellowAngle = 100,
                        sweepYellowAngle = 20,
                        startRedAngle = 120,
                        sweepRedAngle = 40,
                        startRedAngle2 = 120,
                        sweepRedAngle2 = 40,
                    )
                )
            } else {
                Log.d("SETTINGS1", "startGrayAngle: ${settings!!.startGrayAngle!!}")
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

                Log.d("SETTINGS2", "startGrayAngle: $startGrayAngle")
                speedColorAdapter.setStartGrayAngle(settings!!.startGrayAngle!!)
                speedColorAdapter.setSweepGrayAngle(settings!!.sweepGrayAngle!!)
                speedColorAdapter.setStartGreenAngle(settings!!.startGreenAngle!!)
                speedColorAdapter.setSweepGreenAngle(settings!!.sweepGreenAngle!!)
                speedColorAdapter.setStartYellowAngle(settings!!.startYellowAngle!!)
                speedColorAdapter.setSweepYellowAngle(settings!!.sweepYellowAngle!!)
                speedColorAdapter.setStartRedAngle(settings!!.startRedAngle!!)
                speedColorAdapter.setSweepRedAngle(settings!!.sweepRedAngle!!)
                speedColorAdapter.setStartRedAngle2(settings!!.startRedAngle2!!)
                speedColorAdapter.setSweepRedAngle2(settings!!.sweepRedAngle2!!)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arduinoRepositoryImpl = getKoin().get<ArduinoClientImpl>()
        binding.altTextView.text = "-235"
        binding.speedTextView.text = "-5"

        (arduinoRepositoryImpl as ArduinoClientImpl).counLiveData.observe(viewLifecycleOwner) { data ->
            requireActivity().runOnUiThread {
                if (data.altitude.isNotEmpty()) {
                    alt = data.altitude
                    var formattedString = data.altitude.substring(0, data.altitude.length - 1)
                    if (formattedString.isEmpty() || formattedString == "-") {
                        formattedString = "0"
                    }
                    if (data.pressure != "" && !data.pressure.contains("_")) {
                        currentPressure = data.pressure
                    }
                    binding.altTextView.text = formattedString
                    val lastTwoChars = data.altitude.takeLast(2)
                    val offset = lastTwoChars.toInt()
                    val formattedStringInt = formattedString.toInt()
                    val valueToScroll = ((formattedStringInt / 10) * 10).toString()

                    if (alt.contains("-")) {
                        scrollToValueWithCentering2(valueToScroll, (offset * -2))
                    } else {
                        scrollToValueWithCentering2(valueToScroll, (offset * 2))
                    }
                }
            }
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        binding.recyclerView.adapter = altAdapter

        binding.recyclerView2.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        binding.recyclerView2.adapter = speedAdapter

        binding.recyclerViewColor.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        binding.recyclerViewColor.adapter = speedColorAdapter

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

                                binding.varioView.setUpAnimated(0f)
                                binding.varioViewDown.setDownAnimated(0f)
                            }

                            in 3..200 -> {
                                binding.varioView.setUpAnimated(verticalSpeed.toFloat())
                                binding.varioViewDown.setDownAnimated(0f)
                            }

                            in -3 downTo -200 -> {
                                binding.varioView.setUpAnimated(0f)
                                binding.varioViewDown.setDownAnimated(verticalSpeed.toFloat())
                            }

                            else -> {}
                        }

                    }
                    firstAlt = secondAlt
                    prevTimestamp = currentTimestamp
                }
            }
        }
        initializeLocationManager()
        gpsJob = lifecycleScope.launch(Dispatchers.IO) {
            while (locationManager.isLocationEnabled) {
                delay(200)
                withContext(Dispatchers.Main) {
                    val speedKmPerHourInt = speedKmPerHour.toInt()
                    binding.speedTextView.text = "$speedKmPerHourInt"
                    val lastTwoChars = speedKmPerHourInt.toString().takeLast(1)
                    val offset = lastTwoChars.toInt() * 10
                    val valueToScroll = ((speedKmPerHourInt / 10) * 10).toString()
                    val valueToScrollColor = speedKmPerHourInt.toString()
                    //   Log.d("valueToScroll", "$valueToScrollColor")
                    scrollToValueWithCenteringSpeed(valueToScroll, valueToScrollColor, (offset * 2))
                 //   Log.d("DEGREE", "$degreeX")
                    binding.groundSky.setXX(degreeX)
                    binding.groundSky.setYY(degreeY)

                }
            }
        }

    }

    fun scrollToValueWithCenteringSpeed(
        valueToScrollSpeed: String,
        valueToScrollColor: String,
        offset: Int
    ) {
        val layoutManager = binding.recyclerView2.layoutManager as LinearLayoutManager
        val layoutManagerColor = binding.recyclerViewColor.layoutManager as LinearLayoutManager
        // Находим позицию элемента с заданным значением в altituds
        val positionToScrollSpeed = speed.indexOfFirst { it == valueToScrollSpeed }
        val positionToScrollColor = speedColors.indexOfFirst { it == valueToScrollColor }
        if (positionToScrollSpeed != -1) {
            // Вычисляем смещение, чтобы элемент был посередине экрана
            val targetOffsetSpeed = 300
            val targetOffsetColor = 390
            // Прокручиваем RecyclerView к заданной позиции с учетом смещения
            layoutManager.scrollToPositionWithOffset(
                positionToScrollSpeed,
                targetOffsetSpeed - offset
            )
            layoutManagerColor.scrollToPositionWithOffset(positionToScrollColor, targetOffsetColor)
        }
    }

    fun scrollToValueWithCentering2(valueToScroll: String, offset: Int) {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        // Находим позицию элемента с заданным значением в altituds
        val positionToScroll = altituds.indexOfFirst { it.altitude == valueToScroll }

        if (positionToScroll != -1) {
            // Вычисляем смещение, чтобы элемент был посередине экрана

            val targetOffset = 300

            // Прокручиваем RecyclerView к заданной позиции с учетом смещения
            layoutManager.scrollToPositionWithOffset(positionToScroll, targetOffset - offset)
        }
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
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            MIN_TIME_BETWEEN_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES,
            locationListener
        )
        val sensorManager2 =
            requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager2.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val gyroscope = sensorManager2.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        val accelGiroListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> acsel = event.values.clone()
                    Sensor.TYPE_GYROSCOPE -> giro = event.values.clone()
                }

                SensorManager.getRotationMatrix(gravity, magnetic, acsel, giro)
                var outGravity = FloatArray(9)
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_X,
                    SensorManager.AXIS_Z,
                    outGravity
                )
                SensorManager.getOrientation(outGravity, valuesOrientation)
                degreeX = valuesOrientation[2] * 57.2958f
                SensorManager.remapCoordinateSystem(
                    gravity,
                    SensorManager.AXIS_Y,
                    SensorManager.AXIS_Z,
                    outGravity
                )
                SensorManager.getOrientation(outGravity, valuesOrientation)
                degreeY = (valuesOrientation[2] * 57.2958f) / 100
                Log.d("DEGREE_Y", "${valuesOrientation[2] * 57.2958f}")
             //   Log.d("DEGREE_X", "$degreeX")
//                binding.groundSky.setXX(degree)
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // Обработка изменения точности
            }
        }

        sensorManager2.registerListener(
            accelGiroListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager2.registerListener(
            accelGiroListener,
            gyroscope,
            SensorManager.SENSOR_DELAY_NORMAL
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

    override fun onStop() {
        super.onStop()
        gpsJob?.cancel()
    }


    companion object {
        fun newInstance() = GeneralFragment()
        private const val REQUEST_CODE = 1
        private const val MIN_TIME_BETWEEN_UPDATES: Long = 100 // 0.1 секунда
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES = 0.1f // 10 метров
    }
}