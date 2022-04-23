package com.nek12.beautylab.core.model.net.news

@kotlinx.serialization.Serializable
data class EditNewsRequest(
    val title: String,
    val content: String,
    val imageUrl: String?,
)
