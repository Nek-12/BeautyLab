package com.nek12.beautylab.core.model.net.user

@kotlinx.serialization.Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)
