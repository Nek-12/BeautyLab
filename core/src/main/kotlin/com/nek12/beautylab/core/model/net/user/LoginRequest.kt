package com.nek12.beautylab.core.model.net.user

@kotlinx.serialization.Serializable
data class LoginRequest(
    val username: String,
    val password: String,
)
