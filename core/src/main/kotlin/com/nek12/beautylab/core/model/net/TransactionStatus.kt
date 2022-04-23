package com.nek12.beautylab.core.model.net

@kotlinx.serialization.Serializable
enum class TransactionStatus {

    PENDING,
    COMPLETED,
    CANCELED,
}
