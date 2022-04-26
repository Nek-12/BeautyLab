package com.nek12.beautylab.ui.screens.product.list

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class ProductListState : MVIState {

    object Loading : ProductListState()
    object Empty : ProductListState()
    data class Error(val e: Throwable?) : ProductListState()
}

@Immutable
sealed class ProductListIntent : MVIIntent

@Immutable
sealed class ProductListAction : MVIAction {

    object GoBack : ProductListAction()
}
