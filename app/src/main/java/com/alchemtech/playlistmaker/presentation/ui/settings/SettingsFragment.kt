package com.alchemtech.playlistmaker.presentation.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.fragment.app.Fragment
import com.alchemtech.playlistmaker.databinding.FragmentSettingsBinding
import com.alchemtech.playlistmaker.presentation.ui.settings.model.SettingsFragmentModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment() {
    private val viewModel: SettingsFragmentModel by viewModel()
    private var binding: FragmentSettingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backButWork()
        toSupportButWork()
        shareAppButWork()
        termsOfUseButWork()
        darkThemeSwitchWork()
    }

    override fun onPause() {
        super.onPause()
        viewModel.setDarkThemeState()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }


    private fun darkThemeSwitchWork() {
        binding?.dayNightSwitch?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDefaultNightMode(MODE_NIGHT_YES)
            } else {
                setDefaultNightMode(MODE_NIGHT_NO)
            }
        }
        setThemeSwitcherChecked()
    }

    private fun termsOfUseButWork() {
        binding?.buttonTermsOfUse?.setOnClickListener {
            viewModel.openTermsOfUse()
        }
    }

    private fun shareAppButWork() {
        binding?.buttonShareApp?.setOnClickListener {
            viewModel.shareApp()
        }
    }

    private fun toSupportButWork() {
        binding?.buttonToSupport?.setOnClickListener {
            viewModel.openSupport()
        }
    }

    private fun backButWork() {
        binding?.pageSettingsPreview?.setOnClickListener {
        }
    }

    private fun setThemeSwitcherChecked() {
        binding?.dayNightSwitch?.isChecked =
            getDefaultNightMode() == MODE_NIGHT_YES
    }
}
