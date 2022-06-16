package com.nek12.beautylab.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource.LoadParams
import com.nek12.androidutils.extensions.core.ApiResult
import com.nek12.androidutils.extensions.core.map
import com.nek12.androidutils.extensions.core.onSuccess
import com.nek12.beautylab.core.model.net.PageResponse
import com.nek12.beautylab.core.model.net.SortDirection
import com.nek12.beautylab.core.model.net.product.GetProductsFilteredRequest
import com.nek12.beautylab.core.model.net.product.ProductSort
import com.nek12.beautylab.core.model.net.transaction.CreateTransactionRequest
import com.nek12.beautylab.core.model.net.user.AuthTokensResponse
import com.nek12.beautylab.core.model.net.user.LoginRequest
import com.nek12.beautylab.core.model.net.user.SignupRequest
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.net.PagedResultSource
import com.nek12.beautylab.data.net.api.BeautyLabApi
import java.util.*

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

    suspend fun logOut() = api
        .logOut()
        .also { authManager.reset() }

    fun getProducts(request: GetProductsFilteredRequest, sort: ProductSort, direction: SortDirection) =
        paged { api.getProducts(request, key, loadSize, sort.value, direction.value) }

    suspend fun getBrands() = api.getBrands()

    suspend fun getCategories() = api.getCategories()

    suspend fun getFavorites() = api.getFavorites()

    suspend fun getProduct(id: UUID) = api.getProduct(id)

    suspend fun getFavorite(productId: UUID) = getFavorites().map { list ->
        // not a very good approach, but there's not getFavoriteById method on BE
        list.firstOrNull { it.product.id == productId }
    }

    suspend fun addFavorite(productId: UUID) = api.addFavorite(productId)

    suspend fun removeFavorite(favoriteId: UUID) = api.deleteFavorite(favoriteId)

    suspend fun placeOrder(productId: UUID, desiredAmount: Long, comment: String? = null) =
        api.createTransaction(CreateTransactionRequest(productId, desiredAmount, comment))

    suspend fun getUser() = api.getSelf()

    suspend fun getPendingTransactions() = api.getPendingOwnTransactions()

    fun getOwnTransactions() = paged { api.getOwnTransactions(key, loadSize) }

    suspend fun cancelOrder(id: UUID) = api.cancelTransaction(id)

    fun getNews() = paged { api.getNews(key, loadSize) }


    private fun <T: Any> paged(call: suspend LoadParams<Int>.() -> ApiResult<PageResponse<T>>) = Pager(
        config = PagingConfig(BeautyLabApi.PAGE_SIZE),
        pagingSourceFactory = { PagedResultSource(call) },
    ).flow

    private fun <T> ApiResult<T>.saveTokens(selector: (T) -> AuthTokensResponse) = onSuccess {
        val (access, refresh) = selector(it)
        authManager.saveTokens(access, refresh)
    }

    suspend fun getTransaction(id: UUID) = api.getTransaction(id)
}
