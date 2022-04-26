package com.nek12.beautylab.ui.screens.cart

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class CartState : MVIState {

    object Loading : CartState()
    object Empty : CartState()
    data class Error(val e: Throwable?) : CartState()
}

@Immutable
sealed class CartIntent : MVIIntent

@Immutable
sealed class CartAction : MVIAction {

    object GoBack : CartAction()
}
