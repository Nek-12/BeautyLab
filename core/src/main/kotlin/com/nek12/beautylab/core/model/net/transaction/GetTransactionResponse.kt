@file:UseSerializers(UUIDSerializer::class, InstantSerializer::class)

package com.nek12.beautylab.core.model.net.transaction

import com.nek12.beautylab.core.model.net.TransactionStatus
import com.nek12.beautylab.core.model.net.product.GetProductResponse
import com.nek12.beautylab.core.serializers.InstantSerializer
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.util.*

@kotlinx.serialization.Serializable
data class GetTransactionResponse(
    val product: GetProductResponse?,
    val buyerId: UUID?,
    val pricePerUnit: Double,
    val totalPrice: Double,
    val amount: Long,
    val date: Instant,
    val status: TransactionStatus,
    val comment: String? = null,
    val id: UUID,
)
