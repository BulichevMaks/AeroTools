package com.formgrav.aerotools.ui.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.media.ToneGenerator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.formgrav.aerotools.R
import com.formgrav.aerotools.data.datasourse.ArduinoClientImpl
import com.formgrav.aerotools.databinding.FragmentGeneralBinding
import com.formgrav.aerotools.databinding.FragmentMapBinding
import com.formgrav.aerotools.domain.model.AttitudeData
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.adapters.AltAdapter
import com.formgrav.aerotools.ui.rootfragments.DownRootFragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import java.util.ArrayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.getKoin

class GeneralFragment : Fragment() {
    private lateinit var binding: FragmentGeneralBinding
    private lateinit var arduinoRepositoryImpl: ArduinoClientImpl
    private var alt = "0"
    private var currentPressure = ""
    private var altituds: ArrayList<AttitudeData> = arrayListOf()
    private var adapter = AltAdapter(altituds)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startValue = -250
        val endValue = 10350
        val step = 10

        for (value in startValue..endValue step step) {
            val attitudeData = AttitudeData(value.toString(), "0")
            altituds.add(attitudeData)
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
        binding.altTextView.text = "9999"
        (arduinoRepositoryImpl as ArduinoClientImpl).counLiveData.observe(viewLifecycleOwner) { data ->
            requireActivity().runOnUiThread {
                if (data.altitude.isNotEmpty()) {
                    alt = data.altitude

                    Log.d("CHILD5", "$alt")
                    var formattedString = data.altitude.substring(0, data.altitude.length - 1)
                    if (formattedString.isEmpty() || formattedString == "-") {
                        formattedString = "0"
                    }
                    if (data.pressure != "" && !data.pressure.contains("_")) {
                        currentPressure = data.pressure
                    }
                    Log.d("CHILD5", "$formattedString")
                    binding.altTextView.text = formattedString
                    val lastTwoChars = data.altitude.takeLast(2)
                    val offset = lastTwoChars.toInt()
                    val formattedStringInt = formattedString.toInt()
                    val valueToScroll = ((formattedStringInt / 10) * 10).toString()

                   // scrollToValueWithCentering2(valueToScroll, ((offset * 10) * 2))
                    scrollToValueWithCentering2(valueToScroll, (offset * 2))

                }
            }
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        binding.recyclerView.adapter = adapter

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


    }

    fun scrollToValueWithCentering3(valueToScroll: String, offset: Int) {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        // Находим позицию элемента с заданным значением в altituds
        val positionToScroll = altituds.indexOfFirst { it.altitude == valueToScroll }

        if (positionToScroll != -1) {
            // Вычисляем смещение, чтобы элемент был посередине экрана
            val targetOffset = 300

            // Создаем аниматор для плавной анимации смещения
            val animator = ValueAnimator.ofInt(offset, targetOffset - offset)
            animator.duration = 1000 // Длительность анимации в миллисекундах

            // Устанавливаем обновление анимации
            animator.addUpdateListener { animation ->
                val offsetValue = animation.animatedValue as Int
                layoutManager.scrollToPositionWithOffset(positionToScroll, offsetValue)
            }

            // Запускаем анимацию
            animator.start()
        }
    }

    fun scrollToValueWithCentering(valueToScroll: String, offset: Int) {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager

        // Находим позицию элемента с заданным значением в altituds
        val positionToScroll = altituds.indexOfFirst { it.altitude == valueToScroll }

        if (positionToScroll != -1) {
            // Вычисляем смещение, чтобы элемент был посередине экрана

            val screenHeight = binding.recyclerView.height
            Log.d("CHILD1", "$screenHeight")
            val itemHeight = binding.recyclerView.getChildAt(0)?.height ?: 0
            Log.d("CHILD2", "$itemHeight")
            val targetOffset = (screenHeight - itemHeight) / 2
            Log.d("CHILD3", "$targetOffset")
            Log.d("CHILD4", "$positionToScroll")
            Log.d("CHILD5", "${targetOffset - offset}")

            // Прокручиваем RecyclerView к заданной позиции с учетом смещения
            layoutManager.scrollToPositionWithOffset(positionToScroll, targetOffset - offset)
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


    companion object {
        fun newInstance() = GeneralFragment()
    }
}