package ru.kiryanav.ui.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vlnny.base.viewModel.BaseViewModel

abstract class BaseErrorViewModel<InputError, OutputError> : BaseViewModel() {

    protected val errorLiveData = MutableLiveData<OutputError>()
    val error: LiveData<OutputError>
        get() = errorLiveData

    abstract fun defineErrorType(error: InputError?): OutputError
}