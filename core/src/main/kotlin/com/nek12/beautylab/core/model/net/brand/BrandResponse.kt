@file:UseSerializers(UUIDSerializer::class)

package com.nek12.beautylab.core.model.net.brand

import com.nek12.beautylab.core.model.net.Country
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.util.*


@kotlinx.serialization.Serializable
data class BrandResponse(
    val id: UUID,
    val name: String,
    val country: Country?,
)
