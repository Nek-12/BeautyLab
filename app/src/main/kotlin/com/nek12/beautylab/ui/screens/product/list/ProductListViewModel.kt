package com.nek12.beautylab.ui.screens.product.list

import androidx.paging.PagingData
import androidx.paging.map
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.screens.product.list.ProductListAction.GoToAboutApp
import com.nek12.beautylab.ui.screens.product.list.ProductListIntent.ClickedFilters
import com.nek12.beautylab.ui.screens.product.list.ProductListIntent.ClickedInfo
import com.nek12.beautylab.ui.screens.product.list.ProductListIntent.ClickedProduct
import com.nek12.beautylab.ui.screens.product.list.ProductListIntent.SelectedFilters
import com.nek12.beautylab.ui.screens.product.list.ProductListState.DisplayingContent
import com.nek12.beautylab.ui.screens.product.list.ProductListState.Error
import com.nek12.beautylab.ui.screens.product.list.ProductListState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductListViewModel(
    initialFilters: FiltersPayload?,
    private val repo: BeautyLabRepo,
): MVIViewModel<ProductListState, ProductListIntent, ProductListAction>(Loading) {

    private var currentFilters: FiltersPayload = initialFilters ?: FiltersPayload()
    override fun recover(from: Exception) = Error(from)

    init {
        set(DisplayingContent(getProductsData()))
    }


    override suspend fun reduce(intent: ProductListIntent): ProductListState = when (intent) {
        is ClickedFilters -> {
            send(ProductListAction.GoToFilters(currentFilters))
            currentState
        }
        is ClickedProduct -> {
            send(ProductListAction.GoToProductDetails(intent.item.id))
            currentState
        }
        is SelectedFilters -> {
            currentFilters = intent.filters
            DisplayingContent(getProductsData())
        }
        is ClickedInfo -> {
            send(GoToAboutApp)
            currentState
        }
    }

    private fun getProductsData(): Flow<PagingData<ProductCardItem>> {
        return repo.getProducts(currentFilters.toRequest(), currentFilters.sort, currentFilters.direction)
            .map { data -> data.map { ProductCardItem(it) } }
    }
}
