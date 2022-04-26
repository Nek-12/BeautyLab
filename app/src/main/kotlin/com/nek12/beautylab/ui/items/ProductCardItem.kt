package com.nek12.beautylab.ui.items

import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.nek12.beautylab.common.toComposeColor
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import java.util.*

data class ProductCardItem(
    val title: String,
    val imageUrl: Uri?,
    val amountAvailable: Long,
    val price: Double,
    val brandName: String,
    val categoryName: String,
    val color: Color?,
    val id: UUID,
) {

    constructor(product: GetProductResponse) : this(
        product.name,
        product.imageUrl?.toUri(),
        product.amountAvailable,
        product.price,
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
    Card(onClick = onClick, modifier = Modifier) {
        Row {

        }
    }
}


@Composable
@Preview(name = "ProductAdCardItem", showSystemUi = false, showBackground = true)
private fun ProductAdCardItemPreview() {

}
