package ru.kiryanav.ui.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.kiryanav.ui.utils.SingleLiveEvent
import vlnny.base.viewModel.BaseViewModel

abstract class BaseErrorViewModel<InputError, OutputError> : BaseViewModel() {

    protected val errorLiveData = MutableLiveData<OutputError>()
    val error: LiveData<OutputError>
        get() = errorLiveData

    abstract fun defineErrorType(error: InputError?): OutputError

    protected fun checkAndClearError(){
        errorLiveData.value?.let {
            errorLiveData.value = null
        }
    }
}