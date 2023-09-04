package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentGrayLineBinding
import com.formgrav.aerotools.databinding.FragmentGreenLineBinding

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

    companion object {
        fun newInstance() =
            GreenLineFragment()
    }
}