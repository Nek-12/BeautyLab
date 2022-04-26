@file:UseSerializers(UUIDSerializer::class, InstantSerializer::class)

package com.nek12.beautylab.core.model.net.product

import com.nek12.beautylab.core.serializers.InstantSerializer
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.util.*

@kotlinx.serialization.Serializable
data class GetProductsFilteredRequest(
    val brandId: UUID? = null,
    val categoryId: UUID? = null,
    val minDiscount: Float = 0f,
    val maxDiscount: Float = 1f,
    val minPrice: Double = 0.0,
    val maxPrice: Double? = null,
    val isActive: Boolean = true,
    val createdBefore: Instant = Instant.now(),
    val createdAfter: Instant? = null,
    val minAmountAvailable: Long = 0,
)
