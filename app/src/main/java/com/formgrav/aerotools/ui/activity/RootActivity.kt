package com.formgrav.aerotools.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.ActivityRootBinding
import com.formgrav.aerotools.ui.fragment.AirSpeedFragment


class RootActivity: AppCompatActivity() {


    private lateinit var _binding: ActivityRootBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment1 = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1) as NavHostFragment
        val navController = navHostFragment1.navController
        val navHostFragment2 = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView2) as NavHostFragment
        val navController2 = navHostFragment2.navController

    }

    fun receiveStartFromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartFromSettings(start)
            }
        }
    }
    fun receiveEndFromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndFromSettings(end)
            }
        }
    }
}

