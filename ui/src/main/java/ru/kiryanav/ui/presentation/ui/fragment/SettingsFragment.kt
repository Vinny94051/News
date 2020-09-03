package ru.kiryanav.ui.presentation.ui.fragment

import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentSettingsBinding
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion

class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>() {

    companion object : BaseFragmentCompanion<SettingsFragment>(){
        override fun newInstance(): SettingsFragment = SettingsFragment()
    }

    override fun layoutId(): Int = R.layout.fragment_settings
}