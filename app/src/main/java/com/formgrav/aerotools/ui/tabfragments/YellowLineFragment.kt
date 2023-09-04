package com.formgrav.aerotools.ui.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.FragmentGreenLineBinding
import com.formgrav.aerotools.databinding.FragmentYellowLineBinding

class YellowLineFragment: Fragment() {
    private lateinit var binding: FragmentYellowLineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYellowLineBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() =
            YellowLineFragment()
    }
}