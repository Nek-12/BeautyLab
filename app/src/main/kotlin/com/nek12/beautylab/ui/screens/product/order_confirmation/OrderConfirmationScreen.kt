package com.nek12.beautylab.ui.screens.product.order_confirmation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.common.input.Form.Comment
import com.nek12.beautylab.data.util.ApiError.Unauthorized
import com.nek12.beautylab.ui.items.ProductCardItem
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
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLFab
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLNumberSelector
import com.nek12.beautylab.ui.widgets.BLSpacer
import com.nek12.beautylab.ui.widgets.BLTextInput
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle.BottomSheet
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

@Composable
@Destination(style = BottomSheet::class)
fun OrderConfirmationScreen(
    productId: UUID,
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<OrderConfirmationViewModel> { parametersOf(productId) }) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    OrderConfirmationContent(state)
}

@Composable
private fun MVIIntentScope<OrderConfirmationIntent, OrderConfirmationAction>.OrderConfirmationContent(state: OrderConfirmationState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .heightIn(400.dp),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is Error -> BLErrorView(state.e.genericMessage.string())
            is Loading -> CircularProgressIndicator()
            is DisplayingSuccess -> Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BLIcon(asset = GMRIcon.gmr_check_circle_outline, size = 64.dp)
                Text(
                    text = R.string.order_success_description.string(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Button(onClick = { send(ClickedDismiss) }) {
                    Text(R.string.i_understand.string())
                }
            }
            is DisplayingOrder -> Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = R.string.confirm_order_title.string(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp), style = MaterialTheme.typography.h6
                )

                ProductCardItem(item = state.product, onClick = { /* do nothing */ })
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(R.string.select_amount.string())
                    BLNumberSelector(
                        number = state.selectedAmount,
                        onPlus = { send(ClickedPlus) },
                        onMinus = { send(ClickedMinus) },
                        plusEnabled = state.canAdd,
                        minusEnabled = state.canRemove,
                        buttonSizes = 36.dp, //todo: ability to enter text manually
                    )
                }

                BLSpacer()

                BLTextInput(
                    input = state.comment,
                    onTextChange = { send(ChangedComment(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    lengthRange = Comment.DEFAULT_LENGTH_RANGE,
                    label = R.string.comment.string(),
                    placeholder = R.string.order_comment_example.string(),
                    hint = R.string.order_comment_hint.string(),
                    singleLine = false,
                )

                BLSpacer()

                BLFab(icon = GMRIcon.gmr_check, onClick = { send(ClickedOk) })
            }
            is DisplayingFailure -> Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BLIcon(asset = GMRIcon.gmr_sentiment_dissatisfied, size = 64.dp, tint = MaterialTheme.colors.error)

                Text(
                    text = R.string.order_failure_title.string(),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Text(
                    text = R.string.order_failure_message_template.string(state.reason.string()),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Button(onClick = { send(ClickedDismiss) }) {
                    Text(R.string.order_error_dismiss.string())
                }
            }
        }
    }
}

@Composable
@Preview(name = "Order", showSystemUi = true, showBackground = true)
private fun OrderConfirmationPreview() = ScreenPreview {
    OrderConfirmationContent(state = DisplayingOrder(Mock.product))
}

@Composable
@Preview(name = "Success", showSystemUi = true, showBackground = true)
private fun OrderConfirmationPreviewSuccess() = ScreenPreview {
    OrderConfirmationContent(state = DisplayingSuccess)
}

@Composable
@Preview(name = "Failure", showSystemUi = true, showBackground = true)
private fun OrderConfirmationPreviewFailure() = ScreenPreview {
    OrderConfirmationContent(state = DisplayingFailure(Unauthorized.genericMessage))
}
