package com.nek12.beautylab.ui.screens.product.order_confirmation

import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Error
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import java.util.*

class OrderConfirmationViewModel(
    val productId: UUID,
    val repo: BeautyLabRepo,
): MVIViewModel<OrderConfirmationState, OrderConfirmationIntent, OrderConfirmationAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: OrderConfirmationIntent): OrderConfirmationState = when (intent) {
        else -> TODO()
    }
}
