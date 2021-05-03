package ru.kiryanav.ui.presentation.fragment.news.sources

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.koin.core.KoinComponent
import org.koin.core.inject
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentDialogSourceBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.fragment.settings.OnSourceItemClick
import vlnny.base.fragment.BaseBottomSheetBindableDialogFragment



class SourcesDialogFragment private constructor() :
    BaseBottomSheetBindableDialogFragment<FragmentDialogSourceBinding>(),
    KoinComponent, OnSourceItemClick {

    private val viewModel: SourceViewModel by inject()

    override fun layoutId() = R.layout.fragment_dialog_source

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            this.viewModel = this@SourcesDialogFragment.viewModel
            this.callback = this@SourcesDialogFragment
        }
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadSources()
        viewModel.sourceSaved.observe(this, Observer {
            dismiss()
        })
    }

    override fun selectClick(item: ArticleSourceUI, isChecked: Boolean) {
        if (isChecked) {
            viewModel.saveSource(item)
        }
    }

    @Suppress("JAVA_CLASS_ON_COMPANION")
    companion object {
        fun newInstance(): SourcesDialogFragment = SourcesDialogFragment()
        val id : String = javaClass.name
    }
}