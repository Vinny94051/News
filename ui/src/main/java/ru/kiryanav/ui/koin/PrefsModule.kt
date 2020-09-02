package ru.kiryanav.ui.koin

import org.koin.dsl.module
import ru.kiryanav.ui.prefs.ISharedPrefsManager
import ru.kiryanav.ui.prefs.SharedPrefsManager


val prefsModule = module {
    single<ISharedPrefsManager> {
        SharedPrefsManager(get())
    }
}