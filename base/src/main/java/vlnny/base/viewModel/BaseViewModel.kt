package vlnny.base.viewModel

import androidx.lifecycle.*

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible: LiveData<Boolean>
        get() = _isProgressVisible
}