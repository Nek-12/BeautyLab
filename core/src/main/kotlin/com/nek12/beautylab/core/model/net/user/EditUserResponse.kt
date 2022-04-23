package com.nek12.beautylab.core.model.net.user

@kotlinx.serialization.Serializable
data class EditUserResponse(
    val user: GetUserResponse,
    val tokens: AuthTokensResponse,
)
