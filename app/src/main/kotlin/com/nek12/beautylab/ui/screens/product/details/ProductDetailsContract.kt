package com.nek12.beautylab.ui.screens.product.details

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.nek12.androidutils.extensions.core.toZDT
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.ui.items.BrandChipItem
import com.nek12.beautylab.ui.items.CategoryChipItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import java.time.ZonedDateTime
import java.util.*

@Immutable
sealed class ProductDetailsState: MVIState {

    object Loading: ProductDetailsState()
    object Empty: ProductDetailsState()
    data class Error(val e: Throwable?): ProductDetailsState()
    data class DisplayingProduct(
        val favoriteId: UUID?,
        val title: String,
        val description: String,
        val imageUrl: String?,
        val amountAvailable: Long,
        val properties: String?,
        val price: Double,
        val priceWithDiscount: Double,
        val category: CategoryChipItem,
        val brand: BrandChipItem,
        val color: Color?,
        val createdAt: ZonedDateTime,
        val id: UUID,
        val amountSelected: Int = 1,
        val isFavoriteLoading: Boolean = false,
    ): ProductDetailsState() {

        constructor(favoriteId: UUID?, response: GetProductResponse): this(
            favoriteId = favoriteId,
            title = response.name,
            description = response.description,
            imageUrl = response.imageUrl,
            amountAvailable = response.amountAvailable,
            properties = response.properties,
            price = response.price,
            priceWithDiscount = response.priceWithDiscount,
            category = CategoryChipItem(response.category),
            brand = BrandChipItem(response.brand),
            color = response.color?.value?.let { Color(it) },
            createdAt = response.createdAt.toZDT(),
            id = response.id,
        )

        val isFavorite get() = favoriteId != null
        val canAdd get() = amountSelected <= amountAvailable
        val canRemove get() = amountSelected > 1
    }
}

@Immutable
sealed class ProductDetailsIntent: MVIIntent {

    object ClickedFavorite: ProductDetailsIntent()
    object ClickedBuy: ProductDetailsIntent()
    object ClickedCategory: ProductDetailsIntent()
    object ClickedBrand: ProductDetailsIntent()
}

@Immutable
sealed class ProductDetailsAction: MVIAction {

    object GoBack: ProductDetailsAction()
    data class GoToOrderConfirmation(val id: UUID): ProductDetailsAction()
    data class GoToProductsList(val filters: FiltersPayload): ProductDetailsAction()
}
