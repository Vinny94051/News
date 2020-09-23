package com.kiryanav.domain.koin

import com.kiryanav.domain.prefs.ISharedPrefsManager
import com.kiryanav.domain.prefs.PrefsManager
import org.koin.dsl.module

val prefsManager = module {
    single<ISharedPrefsManager> {
        PrefsManager(get())
    }
}