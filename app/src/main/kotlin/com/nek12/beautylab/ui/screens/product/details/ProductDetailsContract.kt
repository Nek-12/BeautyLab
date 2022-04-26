package com.nek12.beautylab.ui.screens.product.details

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class ProductDetailsState : MVIState {

    object Loading : ProductDetailsState()
    object Empty : ProductDetailsState()
    data class Error(val e: Throwable?) : ProductDetailsState()
}

@Immutable
sealed class ProductDetailsIntent : MVIIntent

@Immutable
sealed class ProductDetailsAction : MVIAction {

    object GoBack : ProductDetailsAction()
}
