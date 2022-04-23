@file:UseSerializers(UUIDSerializer::class)

package com.nek12.beautylab.core.model.net.product.category

import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.util.*

@kotlinx.serialization.Serializable
data class ProductCategoryResponse(
    val name: String,
    val type: String?,
    val id: UUID,
)
