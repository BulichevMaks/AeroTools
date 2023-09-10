package com.formgrav.aerotools.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment

import com.formgrav.aerotools.R
import com.formgrav.aerotools.databinding.ActivityRootBinding
import com.formgrav.aerotools.ui.fragment.AirSpeedFragment
import com.formgrav.aerotools.ui.rootfragments.DownRootFragment


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
    fun findDownRootFragment(): DownRootFragment? {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView2)

        if (containerFragment is NavHostFragment) {
            return containerFragment.childFragmentManager.fragments.firstOrNull { it is DownRootFragment } as? DownRootFragment
        }

        return null
    }
    fun receiveStartGrayFromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartGrayFromSettings(start)
            }
        }
    }
    fun receiveEndGrayFromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndGrayFromSettings(end)
            }
        }
    }
    fun receiveStartYellowFromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartYellowFromSettings(start)
            }
        }
    }
    fun receiveEndYellowFromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndYellowFromSettings(end)
            }
        }
    }

    fun receiveStartRedFromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartRedFromSettings(start)
            }
        }
    }
    fun receiveEndRedFromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndRedFromSettings(end)
            }
        }
    }
    fun receiveStartRed2FromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartRed2FromSettings(start)
            }
        }
    }
    fun receiveEndRed2FromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndRed2FromSettings(end)
            }
        }
    }
    fun receiveStartGreenFromSettings(start: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveStartGreenFromSettings(start)
            }
        }
    }
    fun receiveEndGreenFromSettings(end: Int) {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.receiveEndGreenFromSettings(end)
            }
        }
    }
    fun saveSettings() {
        val containerFragment = supportFragmentManager.findFragmentById(R.id.rootFragmentContainerView1)

        if (containerFragment is NavHostFragment) {
            val currentFragment = containerFragment.childFragmentManager.fragments.firstOrNull()
            val airSpeedFragment = currentFragment?.childFragmentManager?.fragments?.firstOrNull { it is AirSpeedFragment } as? AirSpeedFragment
            if (airSpeedFragment is AirSpeedFragment) {
                airSpeedFragment.saveSettings()
            }
        }
    }



}

