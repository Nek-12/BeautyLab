package com.nek12.beautylab.data.net

import android.content.Context
import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.orNull
import com.nek12.androidutils.extensions.core.wrap
import com.nek12.beautylab.core.BACKEND_URL
import com.nek12.beautylab.core.model.net.user.AuthTokensResponse
import com.nek12.beautylab.core.model.net.user.RefreshTokenRequest
import com.nek12.beautylab.data.accessToken
import com.nek12.beautylab.data.refreshToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.request.*
import io.ktor.http.*

class AuthManager(private val context: Context) {

    val isLoggedIn get() = context.accessToken != null && context.refreshToken != null

    fun saveTokens(accessToken: String, refreshToken: String) {
        context.accessToken = accessToken
        context.refreshToken = refreshToken
    }

    val tokens get() = if (isLoggedIn) BearerTokens(context.accessToken!!, context.refreshToken!!) else null

    suspend fun getTokens(client: HttpClient): BearerTokens? = ApiResult.wrap {
        client.post("${BACKEND_URL}auth/token") {
            contentType(ContentType.Application.Json)
            setBody(RefreshTokenRequest(context.refreshToken ?: return null))
        }.body<AuthTokensResponse>()
    }
        .orNull()
        ?.run { BearerTokens(accessToken, refreshToken) }
        ?.also { saveTokens(it.accessToken, it.refreshToken) }

    fun reset() {
        context.accessToken = null
        context.refreshToken = null
    }
}
