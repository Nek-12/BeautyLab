@file:UseSerializers(InstantSerializer::class, UUIDSerializer::class)

package com.nek12.beautylab.core.model.net.product

import com.nek12.beautylab.core.model.net.Color
import com.nek12.beautylab.core.model.net.brand.BrandResponse
import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import com.nek12.beautylab.core.serializers.InstantSerializer
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.util.*

@kotlinx.serialization.Serializable
data class GetProductResponse(
    val name: String,
    val description: String,
    val imageUrl: String?,
    val amountAvailable: Long,
    val properties: String?,
    val price: Double,
    val priceWithDiscount: Double,
    val category: ProductCategoryResponse,
    val brand: BrandResponse,
    val color: Color?,
    val createdAt: Instant,
    val id: UUID,
)
