package ru.kiryanav.ui.presentation.fragment.settings

import android.os.Bundle
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.kiryanav.domain.prefs.ISharedPrefsManager
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentSettingsBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import ru.kiryanav.ui.presentation.worker.NewsWorkManager
import vlnny.base.ext.hide
import vlnny.base.ext.rotateDefault
import vlnny.base.ext.rotateFromTopToBottom
import vlnny.base.ext.show
import vlnny.base.fragment.BaseBindableFragment


class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>(), OnSourceItemClick,
    KoinComponent {

    private val settingsViewModel by viewModel<SettingsViewModel>()
    private val prefsManager: ISharedPrefsManager by inject()
    private val sourcesForSaving = mutableListOf<ArticleSourceUI>()

    override fun layoutId(): Int = R.layout.fragment_settings

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
            closeFragment()
        })
    }

    private fun closeFragment() {
        findNavController().popBackStack()
    }

    override fun initViews() {
        super.initViews()
        initRecycler()
        sourceLayout.setOnClickListener {
            if (sourcesRecyclerView.isVisible) {
                openSourcesBtn.rotateDefault()
                sourcesRecyclerView.hide()
                saveSourcesBtn.hide()
                notificationsLayout.show()
            } else {
                openSourcesBtn.rotateFromTopToBottom()
                sourcesRecyclerView.show()
                saveSourcesBtn.show()
                notificationsLayout.hide()
            }
        }
        initNotifys()
        saveSourcesBtn.setOnClickListener {
            settingsViewModel.saveSources(sourcesForSaving)
        }
    }

    private fun initNotifys() {
        notificationsLayout.setOnClickListener {
            if (intervalRadioGroup.isVisible) {

                intervalRadioGroup.hide()
                saveNotifyBtn.hide()
                openNotifSettingsBtn.rotateDefault()
            } else {
                intervalRadioGroup.show()
                saveNotifyBtn.show()
                openNotifSettingsBtn.rotateFromTopToBottom()
            }
        }

        when (prefsManager.getInterval()) {
            15 -> {
                setRadioBtnActive(interval15mnts)
            }
            30 -> {
                setRadioBtnActive(interval30mnts)
            }
            60 -> {
                setRadioBtnActive(interval60mnts)
            }
            120 -> {
                setRadioBtnActive(interval120mnts)
            }
            240 -> {
                setRadioBtnActive(interval240mnts)
            }
            480 -> {
                setRadioBtnActive(interval480mnts)
            }
            1440 -> {
                setRadioBtnActive(interval24hours)
            }
        }

        interval15mnts.setOnClickListener {
            prefsManager.setInterval(15)
            createWork()
        }
        interval30mnts.setOnClickListener {
            prefsManager.setInterval(30)
            createWork()
        }
        interval60mnts.setOnClickListener {
            prefsManager.setInterval(60)
            createWork()
        }
        interval120mnts.setOnClickListener {
            prefsManager.setInterval(120)
            createWork()
        }
        interval240mnts.setOnClickListener {
            prefsManager.setInterval(240)
            createWork()
        }
        interval480mnts.setOnClickListener {
            prefsManager.setInterval(480)
            createWork()
        }
        interval24hours.setOnClickListener {
            prefsManager.setInterval(1440)
            createWork()
        }

        saveNotifyBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setRadioBtnActive(btn: RadioButton) {
        btn.isChecked = true
    }

    private fun createWork() {
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            NewsWorkManager.UNIQUE_PERIODIC_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            NewsWorkManager.createPeriodicRequest(prefsManager.getInterval().toLong())
        )
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