package com.nek12.beautylab.core.model.net.user

@kotlinx.serialization.Serializable
data class EditUserRequest(
    val username: String,
    val name: String,
    /**
     * Treats null as if the password has not changed
     */
    val password: String? = null,
)
