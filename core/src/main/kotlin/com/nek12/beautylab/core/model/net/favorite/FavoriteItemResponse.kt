@file:UseSerializers(UUIDSerializer::class, InstantSerializer::class)

package com.nek12.beautylab.core.model.net.favorite

import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.serializers.InstantSerializer
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.util.*

@kotlinx.serialization.Serializable
data class FavoriteItemResponse(
    val product: GetProductResponse,
    val id: UUID,
    val addedDate: Instant,
)
