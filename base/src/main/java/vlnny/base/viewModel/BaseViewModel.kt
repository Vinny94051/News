package vlnny.base.viewModel

import androidx.lifecycle.*
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import vlnny.base.rx.RxTransformers

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val disposableList = CompositeDisposable()

    protected val _isProgressVisible = MutableLiveData(false)
    val isProgressVisible: LiveData<Boolean>
        get() = _isProgressVisible

    protected fun addDisposable(disposable: Disposable) {
        disposableList.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        removeDisposables()
    }

    fun removeDisposables() = disposableList.dispose()

    protected fun <T> getProgressSingleTransformer(): SingleTransformer<T, T> =
        RxTransformers.applySingleBeforeAndAfter(showLoader(), hideLoader())

    private fun <T> showLoader() = Consumer<T> {
        _isProgressVisible.value = true
    }

    private fun hideLoader() = Action {
        _isProgressVisible.value = false
    }

}