package com.nek12.beautylab.data.repo

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.map
import com.nek12.androidutils.extensions.core.onSuccess
import com.nek12.beautylab.core.model.net.user.AuthTokensResponse
import com.nek12.beautylab.core.model.net.user.LoginRequest
import com.nek12.beautylab.core.model.net.user.SignupRequest
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.net.api.BeautyLabApi

class BeautyLabRepo(private val api: BeautyLabApi, private val authManager: AuthManager) {

    suspend fun logIn(username: String, password: String) = api
        .logIn(LoginRequest(username, password))
        .saveTokens { it.tokens }
        .map { it.user }


    suspend fun signUp(username: String, name: String, password: String) = api
        .signUp(SignupRequest(username, password, name))
        .saveTokens { it.tokens }
        .map { it.user }

    suspend fun getMainScreen() = api.mainView()

    private fun <T> ApiResult<T>.saveTokens(selector: (T) -> AuthTokensResponse) = onSuccess {
        val (access, refresh) = selector(it)
        authManager.saveTokens(access, refresh)
    }

}
