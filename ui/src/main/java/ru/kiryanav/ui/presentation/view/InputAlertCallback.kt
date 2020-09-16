package ru.kiryanav.ui.presentation.view

import androidx.appcompat.app.AlertDialog


interface InputAlertCallback {
    fun onAlertPositiveSaveClick(data : String, alert : AlertDialog)
}