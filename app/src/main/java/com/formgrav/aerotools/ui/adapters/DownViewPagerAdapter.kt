package com.formgrav.aerotools.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.formgrav.aerotools.ui.fragment.AirSpeedFragment
import com.formgrav.aerotools.ui.fragment.GpsSpeedFragment
import com.formgrav.aerotools.ui.fragment.SettingsFragment
import com.formgrav.aerotools.ui.fragment.VarioFragment

class DownViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SettingsFragment.newInstance()
            1 -> VarioFragment.newInstance()
            else -> GpsSpeedFragment.newInstance()
        }
    }
}