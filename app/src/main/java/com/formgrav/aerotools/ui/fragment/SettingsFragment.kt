package com.formgrav.aerotools.ui.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.formgrav.aerotools.databinding.FragmentSettingsBinding
import com.formgrav.aerotools.ui.activity.RootActivity
import com.formgrav.aerotools.ui.adapters.AirSettingsPagerAdapter
import com.formgrav.aerotools.ui.customview.ButtonDrawable
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabLayout = binding.tabLayout
        val viewPager = binding.viewPager

        val adapter = AirSettingsPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Настройка заголовков вкладок
            tab.text = when (position) {
                0 -> "Gray line"
                1 -> "Green line"
                2 -> "Yellow line"
                3 -> "Red line"
                4 -> "Red line2"
                else -> ""
            }
        }.attach()

        binding.progressBar.max = 100
        binding.progressBar.progress = 0
        val drawable = ButtonDrawable()
        drawable.text = "SAVE"
        binding.buttonSave.background = drawable
        binding.buttonSave.setOnClickListener {
            if (activity is RootActivity) {
                (activity as RootActivity).saveSettings()
            }
            drawable.setPressed(true)
            binding.linearLayout.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                var progress = 0
                while (binding.progressBar.progress < 100) {
                    delay(10)
                    progress++
                    withContext(Dispatchers.Main) {
                        binding.progressBar.progress = progress
                    }
                }
                withContext(Dispatchers.Main) {
                    drawable.setPressed(false)
                    binding.linearLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.progressBar.progress = 0
                }
            }
        }
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}