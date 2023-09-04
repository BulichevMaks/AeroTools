package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentGrayLineBinding
import com.formgrav.aerotools.databinding.FragmentGreenLineBinding
import com.formgrav.aerotools.ui.activity.RootActivity

class GreenLineFragment : Fragment() {
    private lateinit var binding: FragmentGreenLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGreenLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPicker.minValue = 1
        binding.numberPicker.maxValue = 160
        binding.numberPicker.value = 60

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.resultTextView1.text = "value: $newVal"
            if (activity is RootActivity) {
                (activity as RootActivity).receiveStartGreenFromSettings(newVal)
            }
        }

        binding.numberPicker2.minValue = 1
        binding.numberPicker2.maxValue = 160
        binding.numberPicker2.value = 40

        binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
            binding.resultTextView2.text = "value: $newVal"
            if (activity is RootActivity) {
                (activity as RootActivity).receiveEndGreenFromSettings(newVal)
            }
        }
    }

    companion object {
        fun newInstance() =
            GreenLineFragment()
    }
}