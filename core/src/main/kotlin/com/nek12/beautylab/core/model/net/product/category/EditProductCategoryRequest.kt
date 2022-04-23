package com.nek12.beautylab.core.model.net.product.category

@kotlinx.serialization.Serializable
data class EditProductCategoryRequest(
    val name: String,
    val type: String?,
)
