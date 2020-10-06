package ru.kiryanav.ui.presentation.fragment.settings

sealed class SettingsError {
    object Unknown : SettingsError()
    object BadApiKey : SettingsError()
}