package com.nek12.beautylab.ui.screens.orders.cancel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import com.nek12.beautylab.data.util.ApiError
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderAction.GoBack
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedConfirmCancellation
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedDeclineCancellation
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderIntent.ClickedDismiss
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.CancellingOrder
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Failed
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Loading
import com.nek12.beautylab.ui.screens.orders.cancel.CancelOrderState.Success
import com.nek12.beautylab.ui.widgets.BLFab
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLTwoFabsLayout
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
fun CancelOrderScreen(
    orderId: UUID,
    navigator: DestinationsNavigator
) = MVIComposable(getViewModel<CancelOrderViewModel> { parametersOf(orderId) }) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    CancelOrderContent(state)
}

@Composable
private fun MVIIntentScope<CancelOrderIntent, CancelOrderAction>.CancelOrderContent(state: CancelOrderState) {
    Box(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 400.dp)
            .padding(8.dp), contentAlignment = Alignment.Center
    ) {
        when (state) {
            is Loading -> CircularProgressIndicator()
            is CancellingOrder -> {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    TransactionItem(state.order, {}, {}, isActionVisible = false)

                    Text(
                        text = R.string.order_cancellation_confirmation.string(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        textAlign = TextAlign.Center,
                    )

                    BLTwoFabsLayout(
                        modifier = Modifier.padding(8.dp),
                        firstIcon = GMRIcon.gmr_delete_forever,
                        secondIcon = GMRIcon.gmr_close,
                        onFirstClick = { send(ClickedConfirmCancellation) },
                        onSecondClick = { send(ClickedDeclineCancellation) }
                    )

                }
            }
            is Failed -> {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                    BLIcon(asset = GMRIcon.gmr_error_outline, size = 52.dp, modifier = Modifier.padding(32.dp))

                    Text(
                        text = R.string.unable_to_cancel_order.string(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = R.string.error_details_template.string(state.reason.genericMessage.string()),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            is Success -> {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                    BLIcon(asset = GMRIcon.gmr_check_circle_outline, size = 52.dp, modifier = Modifier.padding(32.dp))

                    Text(
                        text = R.string.cancel_success_description.string(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center
                    )

                    BLFab(icon = GMRIcon.gmr_close, onClick = { send(ClickedDismiss) })
                }
            }
        }
    }
}

@Composable
@Preview(name = "CancelOrder", showSystemUi = true, showBackground = true)
private fun CancelOrderPreview() = ScreenPreview {
    CancelOrderContent(state = CancellingOrder(TransactionItem(Mock.transaction)))
}

@Composable
@Preview(name = "CancelOrder", showSystemUi = true, showBackground = true)
private fun CancelOrderPreviewSuccess() = ScreenPreview {
    CancelOrderContent(state = Success)
}

@Composable
@Preview(name = "CancelOrder", showSystemUi = true, showBackground = true)
private fun CancelOrderPreviewFailed() = ScreenPreview {
    CancelOrderContent(state = Failed(ApiError.Unauthorized))
}
