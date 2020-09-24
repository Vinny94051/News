package ru.kiryanav.ui.presentation.fragment.settings

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.kiryanav.domain.prefs.NotificationIntervalManager
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import ru.kiryanav.ui.R
import ru.kiryanav.ui.databinding.FragmentSettingsBinding
import ru.kiryanav.ui.model.ArticleSourceUI
import com.kiryanav.domain.model.TimeInterval
import com.kiryanav.domain.worker.NewsWorkManager
import vlnny.base.ext.hide
import vlnny.base.ext.rotateDefault
import vlnny.base.ext.rotateFromTopToBottom
import vlnny.base.ext.show
import vlnny.base.fragment.BaseBindableFragment


class SettingsFragment : BaseBindableFragment<FragmentSettingsBinding>(), OnSourceItemClick,
    KoinComponent {

    private val settingsViewModel by viewModel<SettingsViewModel>()
    private val prefsManager: NotificationIntervalManager by inject()
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
        initSource()
        initNotifys()
    }

    private fun initSource() {
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

        defineDefaultActiveBtn()

        var timeInterval: TimeInterval? = null
        intervalRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.btn15 -> timeInterval = TimeInterval.MINUTES_15
                R.id.btn30 -> timeInterval = TimeInterval.MINUTES_30
                R.id.btn60 -> timeInterval = TimeInterval.HOURS_1
                R.id.btn120 -> timeInterval = TimeInterval.HOURS_2
                R.id.btn240 -> timeInterval = TimeInterval.HOURS_4
                R.id.btn480 -> timeInterval = TimeInterval.HOURS_8
                R.id.btn1440 -> timeInterval = TimeInterval.ONE_DAY
                R.id.turnOffNotify -> timeInterval = null
            }
        }

        saveNotifyBtn.setOnClickListener {
            saveNotificationStatus(timeInterval)
        }
    }

    private fun saveNotificationStatus(timeInterval: TimeInterval?) {
        timeInterval
            ?.let { interval ->
                createWork(interval)
            } ?: WorkManager.getInstance(requireContext())
            .cancelUniqueWork(NewsWorkManager.UNIQUE_PERIODIC_WORK_NAME)

        findNavController().popBackStack()
    }

    private fun defineDefaultActiveBtn() {
        when (prefsManager.getInterval()) {
            TimeInterval.MINUTES_15 -> btn15.isChecked = true
            TimeInterval.MINUTES_30 -> btn30.isChecked = true
            TimeInterval.HOURS_1 -> btn60.isChecked = true
            TimeInterval.HOURS_2 -> btn120.isChecked = true
            TimeInterval.HOURS_4 -> btn240.isChecked = true
            TimeInterval.HOURS_8 -> btn480.isChecked = true
            TimeInterval.ONE_DAY -> btn1440.isChecked = true
        }
    }

    private fun createWork(timeInterval: TimeInterval?) {
        with(prefsManager) {
            setInterval(timeInterval)

            getInterval()?.let { interval ->
                WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                    NewsWorkManager.UNIQUE_PERIODIC_WORK_NAME,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    NewsWorkManager.createPeriodicRequest(interval)
                )
            }
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