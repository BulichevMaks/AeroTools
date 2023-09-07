package com.formgrav.aerotools.ui.fragment

import android.content.Context
import android.content.pm.PackageManager
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.formgrav.aerotools.R
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class GeneralFragment : Fragment() {
    private lateinit var binding: FragmentGeneralBinding
    private var altituds: ArrayList<AttitudeData> = arrayListOf(
        AttitudeData("-300", "100"),
        AttitudeData("-200", "100"),
        AttitudeData("-100", "100"),
        AttitudeData("0", "100"),
        AttitudeData("100", "100"),
        AttitudeData("200", "200"),
        AttitudeData("300", "300"),
        AttitudeData("400", "500"),
        AttitudeData("500", "600"),
        AttitudeData("600", "700"),
        AttitudeData("700", "800"),
        AttitudeData("800", "900"),
        AttitudeData("900", "1000"),
        AttitudeData("1000", "1000"),
        AttitudeData("1100", "1100"),
        AttitudeData("1200", "1100"),
        AttitudeData("1300", "1100"),
        AttitudeData("1400", "1100"),
        AttitudeData("1500", "1100"),
        AttitudeData("1600", "1100"),
    )
    private var adapter = AltAdapter(altituds)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.altTextView.text = "-150"


        binding.recyclerView.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                true
            )
        binding.recyclerView.adapter = adapter

        binding.button.setOnClickListener {

            scrollToValueWithCentering("400", (50 * 2))
            binding.altTextView.text = "450"

        }


    }

    fun scrollToValueWithAnimation(valueToScroll: String) {
        val layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager
        val adapter = binding.recyclerView.adapter as AltAdapter

        // Находим позицию элемента с заданным значением в altituds
        val positionToScroll = altituds.indexOfFirst { it.altitude == valueToScroll }

        if (positionToScroll != -1) {
            // Создаем аниматор для плавной прокрутки
            val smoothScroller = object : LinearSmoothScroller(requireContext()) {
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_END  // Прокручиваем к началу элемента
                }
            }

            // Устанавливаем целевую позицию аниматору
            smoothScroller.targetPosition = positionToScroll

            // Запускаем анимацию прокрутки
            layoutManager.startSmoothScroll(smoothScroller)

            // Вычисляем смещение, чтобы элемент был посередине экрана
            val itemHeight = layoutManager.getChildAt(0)?.height ?: 0
            val screenHeight = binding.recyclerView.height
            val targetOffset = (screenHeight - itemHeight) / 2

            // Прокручиваем RecyclerView к заданной позиции с учетом смещения
            layoutManager.scrollToPositionWithOffset(positionToScroll, targetOffset)
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

            // Прокручиваем RecyclerView к заданной позиции с учетом смещения
            layoutManager.scrollToPositionWithOffset(positionToScroll, targetOffset - offset)
        }


    }


    companion object {
        fun newInstance() = GeneralFragment()
    }
}