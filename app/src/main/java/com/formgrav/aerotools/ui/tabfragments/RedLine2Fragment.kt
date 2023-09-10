package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentRedLine2Binding
import com.formgrav.aerotools.databinding.FragmentRedLineBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.viewmodel.RedLine2ViewModel
import com.formgrav.aerotools.ui.viewmodel.RedLineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedLine2Fragment : Fragment() {
    private lateinit var binding: FragmentRedLine2Binding
    private val vm: RedLine2ViewModel by viewModel()
    private lateinit var settings: Settings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()!!
            delay(500)
            withContext(Dispatchers.Main) {
                binding.numberPicker.value = settings.startRedAngle2!!
                binding.numberPicker2.value = settings.sweepRedAngle2!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRedLine2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 159

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveStartRed2FromSettings(newVal)
            }
            binding.numberPicker2.maxValue = 160 - newVal
        }

        binding.numberPicker2.minValue = 0
        binding.numberPicker2.maxValue = 159

        binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
            if (activity is RootActivity) {
                (activity as RootActivity).receiveEndRed2FromSettings(newVal)
            }
        }
    }

    companion object {
        fun newInstance() =
            RedLine2Fragment()
    }
}