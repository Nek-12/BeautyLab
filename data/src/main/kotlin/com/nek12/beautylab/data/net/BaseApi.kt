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
            this.method = method
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

    protected suspend inline fun <reified T, reified R> get(
        url: String,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = call<T, R>(url, HttpMethod.Get, body, builder)

    protected suspend inline fun <reified T, reified R> post(
        url: String,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = call<T, R>(url, HttpMethod.Post, body, builder)

    protected suspend inline fun <reified T, reified R> put(
        url: String,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = call<T, R>(url, HttpMethod.Put, body, builder)

    protected suspend inline fun <reified T, reified R> delete(
        url: String,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = call<T, R>(url, HttpMethod.Patch, body, builder)

    protected suspend inline fun <reified T, reified R> patch(
        url: String,
        body: R? = null,
        builder: HttpRequestBuilder.() -> Unit = {},
    ) = call<T, R>(url, HttpMethod.Delete, body, builder)


//TODO: Flow call
}
