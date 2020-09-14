package vlnny.base.viewModel

import androidx.lifecycle.*
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import vlnny.base.rx.RxTransformers

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible: LiveData<Boolean>
        get() = _isProgressVisible
}