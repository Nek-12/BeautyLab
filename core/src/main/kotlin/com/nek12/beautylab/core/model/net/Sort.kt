package com.nek12.beautylab.core.model.net


import kotlinx.serialization.Serializable

@Serializable
data class Sort(
    val empty: Boolean = false,
    val sorted: Boolean = false,
    val unsorted: Boolean = false,
)
