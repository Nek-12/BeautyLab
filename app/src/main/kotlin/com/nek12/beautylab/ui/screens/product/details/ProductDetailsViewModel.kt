package com.nek12.beautylab.ui.screens.product.details

import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.*
import com.nek12.flowMVI.android.MVIViewModel

class ProductDetailsViewModel : MVIViewModel<ProductDetailsState, ProductDetailsIntent, ProductDetailsAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: ProductDetailsIntent): ProductDetailsState = when (intent) {
        else -> TODO()
    }
}
