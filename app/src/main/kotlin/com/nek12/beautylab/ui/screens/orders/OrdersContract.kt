package com.nek12.beautylab.ui.screens.orders

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class OrdersState : MVIState {

    object Loading : OrdersState()
    object Empty : OrdersState()
    data class Error(val e: Throwable?) : OrdersState()
}

@Immutable
sealed class OrdersIntent : MVIIntent

@Immutable
sealed class OrdersAction : MVIAction {

    object GoBack : OrdersAction()
}
