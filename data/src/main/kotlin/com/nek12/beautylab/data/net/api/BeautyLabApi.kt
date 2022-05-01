package com.nek12.beautylab.data.net.api

import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.beautylab.core.model.net.PageResponse
import com.nek12.beautylab.core.model.net.brand.BrandResponse
import com.nek12.beautylab.core.model.net.favorite.FavoriteItemResponse
import com.nek12.beautylab.core.model.net.mobile.MobileViewResponse
import com.nek12.beautylab.core.model.net.news.GetNewsResponse
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.model.net.product.GetProductsFilteredRequest
import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import com.nek12.beautylab.core.model.net.transaction.CreateTransactionRequest
import com.nek12.beautylab.core.model.net.transaction.GetTransactionResponse
import com.nek12.beautylab.core.model.net.user.EditUserResponse
import com.nek12.beautylab.core.model.net.user.GetUserResponse
import com.nek12.beautylab.core.model.net.user.LoginRequest
import com.nek12.beautylab.core.model.net.user.SignupRequest
import com.nek12.beautylab.data.net.BaseApi
import io.ktor.client.*
import io.ktor.client.request.*
import java.util.*

class BeautyLabApi(client: HttpClient): BaseApi(client) {

    suspend fun getSelf() = get<GetUserResponse, Any>("user/self/")

    suspend fun signUp(request: SignupRequest) = post<EditUserResponse, SignupRequest>("auth/signup/", request)

    suspend fun logIn(request: LoginRequest) = post<EditUserResponse, LoginRequest>("auth/login/", request)

    suspend fun mainView() = get<MobileViewResponse, Any>("mobile/main/")

    //e.g.: /api/product/?page=0&per_page=5&sort=createdAt&createdAt.dir=desc
    suspend fun getProducts(
        request: GetProductsFilteredRequest,
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
        sort: String? = null,
        sortDirection: String? = null,
    ): ApiResult<PageResponse<GetProductResponse>> = get("product/", request) {
        parameter("page", page)
        parameter("per_page", pageSize)
        parameter("sort", sort)
        parameter("$sort.dir", sortDirection)
    }

    suspend fun getProduct(id: UUID) = get<GetProductResponse, Any>("product/$id")

    suspend fun getFavorite(id: UUID) = get<FavoriteItemResponse, Any>("favorite/$id")

    suspend fun getFavorites() = get<List<FavoriteItemResponse>, Any>("favorite/own")

    suspend fun addFavorite(productId: UUID) = post<FavoriteItemResponse, Any>("favorite/$productId")

    suspend fun deleteFavorite(favoriteId: UUID) = delete<Unit, Any>("favorite/$favoriteId")

    suspend fun createTransaction(request: CreateTransactionRequest) =
        post<GetTransactionResponse, CreateTransactionRequest>("transaction/create", request)

    suspend fun getOwnTransactions(page: Int? = null, pageSize: Int = PAGE_SIZE) =
        get<PageResponse<GetTransactionResponse>, Any>("transaction/own/") {
            parameter("page", page)
            parameter("per_page", pageSize)
        }

    suspend fun getPendingOwnTransactions() = get<List<GetTransactionResponse>, Any>("transaction/own/pending")

    suspend fun getTransaction(id: UUID) = get<GetTransactionResponse, Any>("transaction/$id")

    suspend fun cancelTransaction(id: UUID) = post<GetTransactionResponse, Any>("transaction/cancel/$id")

    suspend fun getNews(
        page: Int? = null,
        pageSize: Int = PAGE_SIZE,
    ) = get<PageResponse<GetNewsResponse>, Any>("news/") {
        parameter("page", page)
        parameter("per_page", pageSize)
    }

    suspend fun getBrands() = get<List<BrandResponse>, Any>("brand/")

    suspend fun getCategories() = get<List<ProductCategoryResponse>, Any>("product/category/")

    companion object {

        const val PAGE_SIZE = 20
    }
}
