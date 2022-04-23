@file:UseSerializers(UUIDSerializer::class)

package com.nek12.beautylab.core.model.net.transaction

import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.util.*

@kotlinx.serialization.Serializable
data class CreateTransactionRequest(
    val productId: UUID,
    val amount: Long,
    val comment: String?,
)
