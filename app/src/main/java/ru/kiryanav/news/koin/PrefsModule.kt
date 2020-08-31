package ru.kiryanav.news.koin

import org.koin.dsl.module
import ru.kiryanav.news.utils.prefs.ISharedPrefsManager
import ru.kiryanav.news.utils.prefs.SharedPrefsManager

val prefsModule = module {
    single<ISharedPrefsManager> {
        SharedPrefsManager(get())
    }
}