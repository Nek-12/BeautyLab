package com.nek12.beautylab.core.model.net


import kotlinx.serialization.Serializable

@Serializable
data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean,
)
