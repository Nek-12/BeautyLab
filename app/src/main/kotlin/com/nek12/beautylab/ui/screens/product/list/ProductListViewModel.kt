package com.nek12.beautylab.ui.screens.product.list

import com.nek12.beautylab.ui.screens.product.list.ProductListState.*
import com.nek12.flowMVI.android.MVIViewModel

class ProductListViewModel : MVIViewModel<ProductListState, ProductListIntent, ProductListAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: ProductListIntent): ProductListState = when (intent) {
        else -> TODO()
    }
}
