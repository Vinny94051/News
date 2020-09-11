package ru.kiryanav.ui.presentation.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentSettingsBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.ui.list.OnSourceItemClick
import ru.kiryanav.ui.presentation.viewmodel.SettingsViewModel
import vlnny.base.ext.hide
import vlnny.base.ext.rotateDefault
import vlnny.base.ext.rotateFromTopToBottom
import vlnny.base.ext.show
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion


class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>(), OnSourceItemClick {

    private val settingsViewModel: SettingsViewModel by viewModel()
    override fun layoutId(): Int = R.layout.fragment_settings
    private val sourcesForSaving = mutableListOf<ArticleSourceUI>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = settingsViewModel
            this.callback = this@SettingsFragment
            loadSourcesByLanguage("ru") //Stub!
            executePendingBindings()
        }
    }

    override fun initViews() {
        super.initViews()
        initRecycler()
        sourceLayout.setOnClickListener {
            if (sourcesRecyclerView.isVisible) {
                openSourcesBtn.rotateDefault()
                sourcesRecyclerView.hide()
                saveSourcesBtn.hide()
            } else {
                openSourcesBtn.rotateFromTopToBottom()
                sourcesRecyclerView.show()
                saveSourcesBtn.show()
            }
        }

        saveSourcesBtn.setOnClickListener {
            settingsViewModel.saveSources(sourcesForSaving)
        }
    }

    override fun selectClick(item: ArticleSourceUI, isChecked: Boolean) {
        if (!isChecked) {
            item.isSelected = false
            sourcesForSaving.remove(item)
            settingsViewModel.deleteSource(item)
        } else {
            if (!item.name!!.contains(" ")) {
                if (isChecked && !sourcesForSaving.contains(item)) {
                    item.isSelected = true
                    sourcesForSaving.add(item)
                }
            } else {
                showSnack(requireContext().getString(R.string.error_saved))
            }
        }
    }

    private fun initRecycler() {
        val dividerItemDecoration = DividerItemDecoration(
            sourcesRecyclerView.context,
            LinearLayoutManager.VERTICAL
        )
        sourcesRecyclerView.addItemDecoration(dividerItemDecoration)
    }

    @Suppress("SameParameterValue")
    private fun loadSourcesByLanguage(language: String) =
        settingsViewModel.loadSourcesByLanguages(language)

    companion object : BaseFragmentCompanion<SettingsFragment>() {
        override fun newInstance(): SettingsFragment = SettingsFragment()
    }


}