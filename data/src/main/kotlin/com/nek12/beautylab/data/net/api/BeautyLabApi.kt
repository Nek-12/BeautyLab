package com.nek12.beautylab.data.net.api

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.beautylab.core.model.net.mobile.MobileViewResponse
import com.nek12.beautylab.core.model.net.user.EditUserResponse
import com.nek12.beautylab.core.model.net.user.LoginRequest
import com.nek12.beautylab.core.model.net.user.SignupRequest
import com.nek12.beautylab.data.net.BaseApi
import io.ktor.client.*
import io.ktor.http.*

class BeautyLabApi(client: HttpClient) : BaseApi(client) {

    suspend fun signUp(request: SignupRequest): ApiResult<EditUserResponse> =
        call("auth/signup/", HttpMethod.Post, request)

    suspend fun logIn(request: LoginRequest): ApiResult<EditUserResponse> =
        call("auth/login/", HttpMethod.Post, request)

    suspend fun mainView() = call<MobileViewResponse, Any>("mobile/", HttpMethod.Get)
}
