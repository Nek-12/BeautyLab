package com.nek12.beautylab.ui.items

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.core.toZDT
import com.nek12.beautylab.R
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.representation
import com.nek12.beautylab.core.model.net.TransactionStatus
import com.nek12.beautylab.core.model.net.TransactionStatus.CANCELED
import com.nek12.beautylab.core.model.net.TransactionStatus.COMPLETED
import com.nek12.beautylab.core.model.net.TransactionStatus.PENDING
import com.nek12.beautylab.core.model.net.transaction.GetTransactionResponse
import com.nek12.beautylab.ui.theme.BeautyLabTheme
import com.nek12.beautylab.ui.widgets.BLDateCaption
import java.time.ZonedDateTime
import java.util.*

data class TransactionItem(
    val productName: String?,
    val imageUrl: String?,
    val amount: Long,
    val totalPrice: Double,
    val date: ZonedDateTime,
    val status: TransactionStatus,
    val comment: String? = null,
    val id: UUID,
    val productId: UUID?,
) {

    constructor(response: GetTransactionResponse): this(
        productName = response.product?.name,
        imageUrl = response.product?.imageUrl,
        amount = response.amount,
        totalPrice = response.totalPrice,
        date = response.date.toZDT(),
        status = response.status,
        comment = response.comment,
        id = response.id,
        productId = response.product?.id
    )

    val isRepeatVisible get() = productId != null
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun TransactionItem(
    item: TransactionItem,
    onClick: () -> Unit,
    onActionClicked: () -> Unit, //action is determined by the caller
    modifier: Modifier = Modifier,
    isActionVisible: Boolean = true,
) {
    Surface(
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(alpha = 0.5f)),
        color = Color.Transparent,
        contentColor = MaterialTheme.colors.onBackground,
        shape = MaterialTheme.shapes.large,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(64.dp)
                        .clip(CircleShape),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = item.productName ?: R.string.unavailable_product.string(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                    )

                    Text(
                        text = R.string.transaction_amount_price_template.string(item.amount, item.totalPrice),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textAlign = TextAlign.Start,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                    )

                }
            }

            AnimatedVisibility(visible = item.comment != null) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(min = 64.dp)
                        .padding(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = item.comment!!,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = R.string.transaction_status_template.string(item.status.representation),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f),
                    maxLines = 1,
                    softWrap = false,
                    textAlign = TextAlign.Center
                )


                AnimatedVisibility(visible = isActionVisible) {
                    AnimatedContent(targetState = item.status) { status ->
                        when (status) {
                            PENDING -> Button(onClick = onActionClicked, modifier = Modifier.weight(0.5f)) {
                                Text(R.string.cancel_transaction.string())
                            }
                            COMPLETED -> AnimatedVisibility(item.isRepeatVisible) {
                                Button(onClick = onActionClicked, modifier = Modifier.weight(0.5f)) {
                                    Text(R.string.repeat.string())
                                }
                            }
                            CANCELED -> {}
                        }
                    }
                }
            }
            BLDateCaption(
                date = item.date,
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}


@Composable
@Preview("TransactionItem", showBackground = true)
private fun TransactionItemPreview() = BeautyLabTheme(true) {
    BeautyLabTheme {
        Box(Modifier.padding(12.dp)) {
            TransactionItem(TransactionItem(response = Mock.transaction), {}, {})
        }
    }
}
