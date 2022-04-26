package com.nek12.beautylab.data.net.api

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.beautylab.core.model.net.PageResponse
import com.nek12.beautylab.core.model.net.mobile.MobileViewResponse
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.model.net.product.GetProductsFilteredRequest
import com.nek12.beautylab.core.model.net.user.EditUserResponse
import com.nek12.beautylab.core.model.net.user.LoginRequest
import com.nek12.beautylab.core.model.net.user.SignupRequest
import com.nek12.beautylab.data.net.BaseApi
import io.ktor.client.*
import io.ktor.client.request.*

class BeautyLabApi(client: HttpClient) : BaseApi(client) {

    suspend fun signUp(request: SignupRequest): ApiResult<EditUserResponse> =
        post("auth/signup/", request)

    suspend fun logIn(request: LoginRequest): ApiResult<EditUserResponse> =
        post("auth/login/", request)

    suspend fun mainView() = get<MobileViewResponse, Any>("mobile/main/")

    //e.g.: /api/product/?page=0&per_page=5&sort=createdAt&createdAt.dir=desc
    suspend fun products(
        request: GetProductsFilteredRequest,
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sort: String? = null,
        sortDirection: String? = null,
    ): ApiResult<PageResponse<GetProductResponse>> = get("/product/", request) {
        parameter("page", page)
        parameter("per_page", pageSize)
        parameter("sort", sort)
        parameter("$sort.dir", sortDirection)
    }

    companion object {

        const val PAGE_SIZE = 20
    }
}
