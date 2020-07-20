package vlnny.base.rx

import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.reactivestreams.Subscription

class RxTransformers {
    companion object {
        fun <T> applySingleBeforeAndAfter(before: Consumer<Disposable>, after: Action) =
            SingleTransformer<T, T> { upstream ->
                upstream
                    .doOnDispose(after)
                    .doOnSubscribe(before)
                    .doAfterTerminate(after)
            }

        fun <T> applyFlowableBeforeAndAfter(before : Consumer<in Subscription>, after : Action) =
            FlowableTransformer<T,T>{ upstream ->
                upstream
                    .doOnSubscribe(before)
                    .doAfterTerminate(after)
            }
    }
}