package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.databinding.FragmentGrayLineBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.viewmodel.AirSpeedViewModel
import com.formgrav.aerotools.ui.viewmodel.GrayLineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class GrayLineFragment : Fragment() {
    private lateinit var binding: FragmentGrayLineBinding
    private val vm: GrayLineViewModel by viewModel()
    private lateinit var settings: Settings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()!!
            delay(500)
            withContext(Dispatchers.Main) {
                binding.numberPicker.value = settings.startGrayAngle!!
                binding.numberPicker2.value = settings.sweepGrayAngle!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGrayLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 160

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveStartGrayFromSettings(newVal)
            }
        }

        binding.numberPicker2.minValue = 1
        binding.numberPicker2.maxValue = 160

        binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveEndGrayFromSettings(newVal)
            }
        }
    }

    companion object {
        fun newInstance() =
            GrayLineFragment()
    }
}