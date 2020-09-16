package ru.kiryanav.ui.presentation.fragment.settings

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentSettingsBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import vlnny.base.ext.hide
import vlnny.base.ext.rotateDefault
import vlnny.base.ext.rotateFromTopToBottom
import vlnny.base.ext.show
import vlnny.base.fragment.BaseBindableFragment


class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>(), OnSourceItemClick {

    private val settingsViewModel by viewModel<SettingsViewModel>()
    override fun layoutId(): Int = R.layout.fragment_settings
    private val sourcesForSaving = mutableListOf<ArticleSourceUI>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = settingsViewModel
            this.callback = this@SettingsFragment
            loadSourcesByLanguage()
            executePendingBindings()
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        settingsViewModel.sourcesSavedNotify.observe(this, Observer {
            findNavController().popBackStack()
        })
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
            if (isChecked && !sourcesForSaving.contains(item)) {
                item.isSelected = true
                sourcesForSaving.add(item)
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
    private fun loadSourcesByLanguage() =
        settingsViewModel.loadSources()
}