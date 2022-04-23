package com.nek12.beautylab.core.model.net.user

import com.nek12.beautylab.core.model.net.UserRole

@kotlinx.serialization.Serializable
data class GetUserResponse(
    val username: String,
    val name: String,
    val bonusBalance: Double,
    val roles: Set<UserRole>,
)
