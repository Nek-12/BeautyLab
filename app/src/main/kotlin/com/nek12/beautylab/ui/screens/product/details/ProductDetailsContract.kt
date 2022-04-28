package com.nek12.beautylab.ui.screens.product.details

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.nek12.androidutils.extensions.core.toZDT
import com.nek12.beautylab.core.model.net.product.GetProductResponse
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
        val isFavorite: Boolean,
        val title: String,
        val description: String,
        val imageUrl: String?,
        val amountAvailable: Long,
        val properties: String?,
        val price: Double,
        val priceWithDiscount: Double,
        val categoryName: String,
        val brandName: String,
        val color: Color?,
        val createdAt: ZonedDateTime,
        val id: UUID,
        val amountSelected: Int = 1,
    ): ProductDetailsState() {

        constructor(isFavorite: Boolean, response: GetProductResponse): this(
            isFavorite,
            response.name,
            response.description,
            response.imageUrl,
            response.amountAvailable,
            response.properties,
            response.price,
            response.priceWithDiscount,
            response.category.name,
            response.brand.name,
            response.color?.value?.let { Color(it) },
            response.createdAt.toZDT(),
            response.id,
        )


        val canAdd get() = amountSelected <= amountAvailable
        val canRemove get() = amountSelected > 1
    }
}

@Immutable
sealed class ProductDetailsIntent: MVIIntent

@Immutable
sealed class ProductDetailsAction: MVIAction {

    object GoBack: ProductDetailsAction()
}
