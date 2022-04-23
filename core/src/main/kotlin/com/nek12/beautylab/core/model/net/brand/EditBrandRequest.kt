package com.nek12.beautylab.core.model.net.brand

import com.nek12.beautylab.core.model.net.Country

@kotlinx.serialization.Serializable
data class EditBrandRequest(
    val name: String,
    val country: Country? = null,
)
