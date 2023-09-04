package com.formgrav.aerotools.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.formgrav.aerotools.ui.tabfragments.GrayLineFragment
import com.formgrav.aerotools.ui.tabfragments.GreenLineFragment
import com.formgrav.aerotools.ui.tabfragments.RedLineFragment
import com.formgrav.aerotools.ui.tabfragments.YellowLineFragment

class AirSettingsPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 4 // Количество вкладок

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GrayLineFragment.newInstance()
            1 -> GreenLineFragment.newInstance()
            2 -> YellowLineFragment.newInstance()
            3 -> RedLineFragment.newInstance()

            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}