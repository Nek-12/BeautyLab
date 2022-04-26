package com.nek12.beautylab.core.model.net


import kotlinx.serialization.Serializable

@Serializable
data class PageResponse<T>(
    val content: List<T>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int,
)
