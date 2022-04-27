package com.nek12.beautylab.ui.screens.product.list

import androidx.paging.PagingData
import androidx.paging.map
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.product.list.ProductListState.*
import com.nek12.flowMVI.android.MVIViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductListViewModel(
    initialFilters: FiltersPayload?,
    private val repo: BeautyLabRepo,
) : MVIViewModel<ProductListState, ProductListIntent, ProductListAction>() {

    private var currentFilters: FiltersPayload = initialFilters ?: FiltersPayload()
    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    init {
        set(DisplayingContent(getProductsData()))
    }


    override suspend fun reduce(intent: ProductListIntent): ProductListState = when (intent) {
        is ProductListIntent.ClickedFilters -> {
            send(ProductListAction.GoToFilters(currentFilters))
            currentState
        }
        is ProductListIntent.ClickedProduct -> {
            send(ProductListAction.GoToProductDetails(intent.item.id))
            currentState
        }
        is ProductListIntent.SelectedFilters -> {
            currentFilters = intent.filters
            DisplayingContent(getProductsData())
        }
    }

    private fun getProductsData(): Flow<PagingData<ProductCardItem>> {
        return repo.getProducts(currentFilters.toRequest(), currentFilters.sort, currentFilters.direction)
            .map { data -> data.map { ProductCardItem(it) } }
    }
}
