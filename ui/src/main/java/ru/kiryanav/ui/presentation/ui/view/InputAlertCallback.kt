package ru.kiryanav.ui.presentation.ui.view

import androidx.appcompat.app.AlertDialog


interface InputAlertCallback {
    fun onAlertPositiveSaveClick(data : String, alert : AlertDialog)
}