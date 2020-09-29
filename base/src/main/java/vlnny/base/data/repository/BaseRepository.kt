package vlnny.base.data.repository

import vlnny.base.data.model.ResponseResult

abstract class BaseRepository {

    suspend fun <T, E> withErrorHandlingCall(call: suspend () -> T): ResponseResult<T, E> =
        try {
            ResponseResult.Success(call.invoke())
        } catch (ex: Throwable) {
            ResponseResult.Error()
        }

}