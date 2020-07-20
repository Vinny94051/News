package vlnny.base.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

fun <T> Flowable<T>.subscribeWithError(
    doOnNext: (T) -> Unit
): Disposable =
    this.subscribe({ item ->
        doOnNext(item)
    }, { ex ->
        ex.printStackTrace()
    })

fun <T> Single<T>.subscribeWithError(
    doOnNext: (T) -> Unit
): Disposable =
    this.subscribe({ item ->
        doOnNext(item)
    }, { ex ->
        ex.printStackTrace()
    })

fun Completable.subscribeWithError(
    doOnNext: () -> Unit
) : Disposable =
    this.subscribe({
        doOnNext()
    },{ ex ->
        ex.printStackTrace()
    })




