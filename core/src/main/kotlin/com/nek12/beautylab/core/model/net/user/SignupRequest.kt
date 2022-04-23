package com.nek12.beautylab.core.model.net.user

@kotlinx.serialization.Serializable
data class SignupRequest(
    val username: String,
    val password: String,
    val name: String,
)
