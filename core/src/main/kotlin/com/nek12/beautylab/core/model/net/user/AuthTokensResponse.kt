@file:UseSerializers(InstantSerializer::class)

package com.nek12.beautylab.core.model.net.user

import com.nek12.beautylab.core.serializers.InstantSerializer
import kotlinx.serialization.UseSerializers
import java.time.Instant

@kotlinx.serialization.Serializable
data class AuthTokensResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: Instant,
)
