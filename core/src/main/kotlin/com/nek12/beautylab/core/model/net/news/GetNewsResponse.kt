@file:UseSerializers(UUIDSerializer::class, InstantSerializer::class)


package com.nek12.beautylab.core.model.net.news

import com.nek12.beautylab.core.serializers.InstantSerializer
import com.nek12.beautylab.core.serializers.UUIDSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant
import java.util.*


@kotlinx.serialization.Serializable
data class GetNewsResponse(
    val title: String,
    val content: String,
    val id: UUID,
    val imageUrl: String? = null,
    val createdAt: Instant,
)
