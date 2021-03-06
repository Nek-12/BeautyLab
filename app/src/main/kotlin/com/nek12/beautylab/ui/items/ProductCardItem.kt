package com.nek12.beautylab.ui.items

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.toComposeColor
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.ui.theme.BeautyLabTheme
import java.util.*

@Immutable
data class ProductCardItem(
    val title: String,
    val imageUrl: Uri?,
    val amountAvailable: Long,
    val price: Double,
    val priceWithDiscount: Double,
    val brandName: String,
    val categoryName: String,
    val color: Color?,
    val id: UUID,
) {

    constructor(product: GetProductResponse): this(
        product.name,
        product.imageUrl?.toUri(),
        product.amountAvailable,
        product.price,
        product.priceWithDiscount,
        product.brand.name,
        product.category.name,
        product.color?.toComposeColor(),
        product.id
    )
}

fun List<GetProductResponse>.cards() = map { ProductCardItem(it) }

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductCardItem(
    item: ProductCardItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick, modifier = modifier
            .padding(8.dp)
            .height(152.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val request = ImageRequest.Builder(LocalContext.current).crossfade(true).data(item.imageUrl).build()
            AsyncImage(
                model = request,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start,
            ) {

                Text(
                    item.title,
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(4.dp)
                        .widthIn(max = 260.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(annotatedPrice(item.price, item.priceWithDiscount))

                Text(R.string.amount_template.string(item.amountAvailable))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Chip({}) { Text(item.brandName, style = MaterialTheme.typography.caption) }
                    Chip({}) { Text(item.categoryName, style = MaterialTheme.typography.caption) }
                }
            }

        }
    }
}


@Composable
fun annotatedPrice(price: Double, priceWithDiscount: Double): AnnotatedString {
    return buildAnnotatedString {
        if (price == priceWithDiscount) {
            append(R.string.price_template.string(price))
        } else {
            withStyle(
                SpanStyle(
                    textDecoration = TextDecoration.LineThrough,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            ) {
                append(R.string.price_template.string(price))
            }
            append(" ")
            withStyle(SpanStyle(color = MaterialTheme.colors.error)) {
                append(R.string.price_template.string(priceWithDiscount))
            }
        }
    }
}


@Composable
@Preview(name = "ProductAdCardItem", showSystemUi = false, showBackground = true)
private fun ProductAdCardItemPreview() {
    BeautyLabTheme {
        ProductCardItem(Mock.product)
    }
}
