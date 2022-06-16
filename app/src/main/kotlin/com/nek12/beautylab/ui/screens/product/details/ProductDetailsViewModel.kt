package com.nek12.beautylab.ui.screens.product.details

import com.nek12.androidutils.extensions.core.map
import com.nek12.androidutils.extensions.core.or
import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.GoToOrderConfirmation
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.GoToProductsList
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedBrand
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedBuy
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedCategory
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedFavorite
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.DisplayingProduct
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.Error
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState
import kotlinx.coroutines.async
import java.util.*

class ProductDetailsViewModel(
    private val productId: UUID,
    private val repo: BeautyLabRepo,
): MVIViewModel<ProductDetailsState, ProductDetailsIntent, ProductDetailsAction>(Loading) {

    override fun recover(from: Exception) = Error(from)

    init {
        loadProduct()
    }

    override suspend fun reduce(intent: ProductDetailsIntent): ProductDetailsState = when (intent) {
        ClickedBrand -> withState<DisplayingProduct> {
            send(GoToProductsList(FiltersPayload(brandId = brand.id)))
            currentState
        }
        ClickedCategory -> withState<DisplayingProduct> {
            send(GoToProductsList(FiltersPayload(categoryId = category.id)))
            currentState
        }
        ClickedBuy -> withState<DisplayingProduct> {
            send(GoToOrderConfirmation(productId))
            currentState
        }
        ClickedFavorite -> withState<DisplayingProduct> {
            toggleFavorite(favoriteId)
            currentState
        }
    }

    private fun toggleFavorite(id: UUID?) = launchForState {

        val newId: UUID? = if (id != null) { //has favorite, remove it
            repo.removeFavorite(id).map { null }.or(id)
        } else {
            repo.addFavorite(productId).map { it.id }.or(id)
        }

        withState<DisplayingProduct> {
            copy(favoriteId = newId)
        }
    }

    private fun loadProduct() = launchForState {
        val product = async { repo.getProduct(productId) }
        val favorite = async { repo.getFavorite(productId) }

        DisplayingProduct(favorite.await().orThrow()?.id, product.await().orThrow())
    }
}
