package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentGreenLineBinding
import com.formgrav.aerotools.databinding.FragmentYellowLineBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.viewmodel.GrayLineViewModel
import com.formgrav.aerotools.ui.viewmodel.YellowLineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class YellowLineFragment : Fragment() {
    private lateinit var binding: FragmentYellowLineBinding
    private val vm: YellowLineViewModel by viewModel()
    private lateinit var settings: Settings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()!!
            delay(500)
            withContext(Dispatchers.Main) {
                binding.numberPicker.value = settings.startYellowAngle!!
                binding.numberPicker2.value = settings.sweepYellowAngle!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYellowLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 159

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveStartYellowFromSettings(newVal)
            }
            binding.numberPicker2.maxValue = 160 - newVal
        }

        binding.numberPicker2.minValue = 0
        binding.numberPicker2.maxValue = 159

        binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveEndYellowFromSettings(newVal)
            }
        }
    }

    companion object {
        fun newInstance() =
            YellowLineFragment()
    }
}