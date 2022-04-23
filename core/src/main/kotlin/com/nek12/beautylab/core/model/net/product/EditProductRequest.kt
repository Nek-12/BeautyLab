@file:UseSerializers(UUIDSerializer::class)

package com.nek12.beautylab.core.model.net.product

import com.nek12.beautylab.core.model.net.Color
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.util.*

@kotlinx.serialization.Serializable
data class EditProductRequest(
    val name: String,
    val description: String,
    val imageUrl: String?,
    val price: Double,
    val amountAvailable: Long,
    val categoryId: UUID,
    val brandId: UUID,
    val color: Color? = null,
    val properties: String? = null,
    val isActive: Boolean = true,
)
