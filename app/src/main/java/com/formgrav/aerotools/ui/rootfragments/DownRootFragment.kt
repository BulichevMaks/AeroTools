package com.formgrav.aerotools.ui.rootfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.formgrav.aerotools.databinding.FragmentDownRootBinding
import com.formgrav.aerotools.ui.adapters.DownViewPagerAdapter
import com.formgrav.aerotools.ui.adapters.UpViewPagerAdapter

class DownRootFragment : Fragment() {
    private lateinit var binding: FragmentDownRootBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDownRootBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = DownViewPagerAdapter(
            fragmentManager = childFragmentManager,
            lifecycle = lifecycle,
        )
        binding.viewPager.setCurrentItem(1, false)
    }
}