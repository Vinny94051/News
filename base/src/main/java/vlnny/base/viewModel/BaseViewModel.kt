package vlnny.base.viewModel

import androidx.lifecycle.*
import vlnny.base.error.ui.Error

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val isProgressVisibleLiveData = MutableLiveData(false)
    val isProgressVisible: LiveData<Boolean>
        get() = isProgressVisibleLiveData

    protected val errorLiveData = MutableLiveData<Error>()
    val error: LiveData<Error>
        get() = errorLiveData
}