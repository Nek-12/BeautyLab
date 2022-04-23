package com.nek12.beautylab.data.net

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.wrap
import com.nek12.beautylab.data.util.apiErrors
import com.nek12.beautylab.data.util.log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

abstract class BaseApi(protected val client: HttpClient) {

    protected suspend inline fun <reified T, reified R> call(
        url: String,
        method: HttpMethod,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = ApiResult.wrap {
        client.request(url) {
            this@request.method = method
            accept(ContentType.Application.Json)

            body?.let {
                contentType(ContentType.Application.Json)
                setBody(it)
            }

            builder()
        }.body<T>()
    }
        .log()
        .apiErrors()

    //TODO: Flow call
}
