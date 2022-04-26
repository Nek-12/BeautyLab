package com.nek12.beautylab.data.util

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.mapError
import io.ktor.client.plugins.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException
import logcat.LogPriority
import logcat.logcat
import java.io.IOException

sealed class ApiError : java.io.IOException() {
    object NoInternet : ApiError()
    object Unauthorized : ApiError()
    object NotFound : ApiError()
    data class SerializationError(override val message: String?) : ApiError()
    data class Unknown(override val message: String?) : ApiError()

}

fun <T> ApiResult<T>.apiErrors() = mapError {
    when (it) {
        is ClientRequestException -> {
            when (it.response.status.value) {
                401, 403 -> ApiError.Unauthorized
                404 -> ApiError.NotFound
                else -> ApiError.Unknown(it.message)
            }
        }
        is SerializationException -> ApiError.SerializationError(it.message)
        is HttpRequestTimeoutException, is TimeoutCancellationException, is IOException -> {
            ApiError.NoInternet
        }
        else -> it
    }
}


fun <T> ApiResult<T>.log() =
    also { logcat(if (isError) LogPriority.ERROR else LogPriority.VERBOSE) { "Result: $this" } }
