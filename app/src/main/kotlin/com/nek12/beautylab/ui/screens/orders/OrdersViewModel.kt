package com.nek12.beautylab.ui.screens.orders

import com.nek12.beautylab.ui.screens.orders.OrdersState.*
import com.nek12.flowMVI.android.MVIViewModel

class OrdersViewModel : MVIViewModel<OrdersState, OrdersIntent, OrdersAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: OrdersIntent): OrdersState = when (intent) {
        else -> TODO()
    }
}
