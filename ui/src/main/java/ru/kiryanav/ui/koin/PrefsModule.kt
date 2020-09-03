package ru.kiryanav.ui.koin

import org.koin.dsl.module
import com.kiryanav.domain.prefs.ISharedPrefsManager
import com.kiryanav.domain.prefs.SharedPrefsManager


val prefsModule = module {
    single<ISharedPrefsManager> {
        SharedPrefsManager(get())
    }
}