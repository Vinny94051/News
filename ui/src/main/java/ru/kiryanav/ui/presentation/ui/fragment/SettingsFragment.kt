package ru.kiryanav.ui.presentation.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
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
import vlnny.base.ext.show
import vlnny.base.fragment.BaseBindableFragment
import vlnny.base.fragment.BaseFragmentCompanion


class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>(), OnSourceItemClick {

    private val settingsViewModel: SettingsViewModel by viewModel()
    override fun layoutId(): Int = R.layout.fragment_settings

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = settingsViewModel
            this.callback = this@SettingsFragment
            loadSourcesByLanguage("ru")
            executePendingBindings()
        }
    }

    override fun initViews() {
        super.initViews()
        initRecycler()
        sourceLayout.setOnClickListener {
            if (sourcesRecyclerView.isVisible) {
                openSourcesBtn.animate().rotation(0f).start()
                sourcesRecyclerView.hide()
            } else {
                openSourcesBtn.animate().rotation(180f).start()
                sourcesRecyclerView.show()
            }
        }
    }

    override fun chooseItem(item: ArticleSourceUI) {
        settingsViewModel.setSource(item)
    }

    override fun initViewModel() {
        super.initViewModel()
        settingsViewModel.isSourceSaved.observe(this, Observer { savedSource ->
            if (savedSource.isSaved) {
                showSnack(
                    getString(R.string.snack_source_changed)
                        .format(savedSource.source.name)
                )
            } else {
                showSnack(getString(R.string.error_something_went_wrong))
            }
        })
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