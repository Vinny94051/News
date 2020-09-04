package ru.kiryanav.ui.koin

import org.koin.dsl.module
import com.kiryanav.domain.prefs.IKeywordManager
import com.kiryanav.domain.prefs.PrefsManager


val prefsModule = module {
    single<IKeywordManager> {
        PrefsManager(get())
    }
}