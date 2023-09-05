package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentRedLineBinding
import com.formgrav.aerotools.databinding.FragmentYellowLineBinding
import com.formgrav.aerotools.domain.model.Settings
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.viewmodel.GrayLineViewModel
import com.formgrav.aerotools.ui.viewmodel.RedLineViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class RedLineFragment : Fragment() {
    private lateinit var binding: FragmentRedLineBinding
    private val vm: RedLineViewModel by viewModel()
    private lateinit var settings: Settings
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            settings = vm.getSettings()!!
            delay(500)
            withContext(Dispatchers.Main) {
                binding.numberPicker.value = settings.startRedAngle!!
                binding.numberPicker2.value = settings.sweepRedAngle!!
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRedLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 160
      //  binding.numberPicker.value = 120

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.resultTextView1.text = "value: $newVal"
            if (activity is RootActivity) {
                (activity as RootActivity).receiveStartRedFromSettings(newVal)
            }
        }

        binding.numberPicker2.minValue = 1
        binding.numberPicker2.maxValue = 160
      //  binding.numberPicker2.value = 40

        binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.resultTextView2.text = "value: $newVal"
            if (activity is RootActivity) {
                (activity as RootActivity).receiveEndRedFromSettings(newVal)
            }
        }
    }

    companion object {
        fun newInstance() =
            RedLineFragment()
    }
}