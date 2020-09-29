package vlnny.base.data.model

import java.lang.Error


sealed class ResponseResult<out T, out E> {
    data class Success<out T>(val value: T) : ResponseResult<T, Nothing>()

    data class Error<out E>(val error: E? = null) : ResponseResult<Nothing, E>()
}

fun <T, E> ResponseResult<T, E>.isError(): Boolean = this is ResponseResult.Error

fun <I, E, O> ResponseResult<I, E>.mapIfSuccess(
    mapRule: (I) -> ResponseResult.Success<O>
): ResponseResult<O, E> {
    return when (this) {
        is ResponseResult.Success<I> -> mapRule(this.value)
        is ResponseResult.Error -> this
    }
}

fun <V, E> ResponseResult<V, E>.successValue(): V? {
    return when (this) {
        is ResponseResult.Success<V> -> this.value
        is ResponseResult.Error -> null
    }
}


fun <T, E> ResponseResult<T, E>.toSuccess() =
    this as ResponseResult.Success