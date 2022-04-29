package com.nek12.beautylab.ui.screens.product.order_confirmation

import androidx.compose.runtime.Immutable
import com.nek12.beautylab.common.Text
import com.nek12.beautylab.common.input.Input
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class OrderConfirmationState: MVIState {

    object Loading: OrderConfirmationState()
    data class Error(val e: Throwable?): OrderConfirmationState()

    object DisplayingSuccess: OrderConfirmationState()

    data class DisplayingFailure(
        val reason: Text,
    ): OrderConfirmationState()

    data class DisplayingOrder(
        val product: ProductCardItem,
        val comment: Input = Input.Empty(),
        val selectedAmount: Long = 1
    ): OrderConfirmationState() {

        constructor(response: GetProductResponse): this(
            ProductCardItem(response)
        )

        val canAdd get() = selectedAmount < product.amountAvailable
        val canRemove get() = selectedAmount > 1
    }
}

@Immutable
sealed class OrderConfirmationIntent: MVIIntent {

    data class ChangedComment(val input: String): OrderConfirmationIntent()

    object ClickedPlus: OrderConfirmationIntent()
    object ClickedMinus: OrderConfirmationIntent()
    object ClickedOk: OrderConfirmationIntent()
    object ClickedDismiss: OrderConfirmationIntent()
}

@Immutable
sealed class OrderConfirmationAction: MVIAction {

    object GoBack: OrderConfirmationAction()
}
