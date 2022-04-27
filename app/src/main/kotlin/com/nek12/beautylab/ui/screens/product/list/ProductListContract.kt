package com.nek12.beautylab.ui.screens.product.list

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import kotlinx.coroutines.flow.Flow
import java.util.*

@Immutable
sealed class ProductListState : MVIState {

    object Loading : ProductListState()
    data class Error(val e: Throwable?) : ProductListState()

    //todo: should test if it does not break composition
    data class DisplayingContent(val data: Flow<PagingData<ProductCardItem>>) : ProductListState()
}

@Immutable
sealed class ProductListIntent : MVIIntent {

    data class ClickedProduct(val item: ProductCardItem) : ProductListIntent()
    object ClickedFilters : ProductListIntent()
    data class SelectedFilters(val filters: FiltersPayload) : ProductListIntent()
}

@Immutable
sealed class ProductListAction : MVIAction {

    object GoBack : ProductListAction()
    data class GoToProductDetails(val id: UUID) : ProductListAction()
    data class GoToFilters(val filters: FiltersPayload) : ProductListAction()
}
