package com.nek12.beautylab.ui.screens.product.order_confirmation

import com.nek12.androidutils.extensions.core.fold
import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationAction.GoBack
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationIntent.ChangedComment
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationIntent.ClickedDismiss
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationIntent.ClickedMinus
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationIntent.ClickedOk
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationIntent.ClickedPlus
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.DisplayingFailure
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.DisplayingOrder
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.DisplayingSuccess
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Error
import com.nek12.beautylab.ui.screens.product.order_confirmation.OrderConfirmationState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState
import java.util.*

class OrderConfirmationViewModel(
    val productId: UUID,
    val repo: BeautyLabRepo,
): MVIViewModel<OrderConfirmationState, OrderConfirmationIntent, OrderConfirmationAction>(Loading) {

    override fun recover(from: Exception) = Error(from)

    init {
        loadProduct()
    }

    private val form = Form.Comment()

    override suspend fun reduce(intent: OrderConfirmationIntent): OrderConfirmationState = when (intent) {
        ClickedDismiss -> {
            send(GoBack)
            currentState
        }
        ClickedMinus -> withState<DisplayingOrder> {
            copy(selectedAmount = selectedAmount - 1)
        }
        ClickedPlus -> withState<DisplayingOrder> {
            copy(selectedAmount = selectedAmount + 1)
        }
        ClickedOk -> withState<DisplayingOrder> {
            val comment = form.validate(this.comment.value)

            if (comment.isValid) {
                placeOrder(selectedAmount, comment.orNull())
                Loading
            } else {
                copy(comment = comment)
            }
        }
        is ChangedComment -> withState<DisplayingOrder> {
            copy(comment = form.validate(intent.input))
        }
    }

    private fun placeOrder(amount: Long, comment: String?) = launchForState {
        repo.placeOrder(productId, amount, comment).fold(
            onSuccess = { DisplayingSuccess },
            onError = { DisplayingFailure(it.genericMessage) }
        )
    }

    private fun loadProduct() = launchForState {
        DisplayingOrder(repo.getProduct(productId).orThrow())
    }
}
