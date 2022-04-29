package com.nek12.beautylab.common

import androidx.compose.runtime.Composable
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.Text.Resource
import com.nek12.beautylab.common.input.ValidationError
import com.nek12.beautylab.common.input.toMessages
import com.nek12.beautylab.data.util.ApiError
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

inline val <reified T: Throwable?> T.genericMessage: Text
    inline get() = when (this) {
        is ApiError.NoInternet -> Resource(R.string.no_internet_error)
        is ApiError.Invalid -> Resource(R.string.invalid_data_error, message ?: toString())
        is ApiError.Unauthorized -> Resource(R.string.log_in_again)
        is ApiError.SerializationError, is ApiError.Unknown -> Resource(R.string.internal_error, message ?: toString())
        else -> Resource(R.string.unknown_error_template, this?.localizedMessage ?: this?.message ?: "")
    }

@Composable
fun List<ValidationError>.toReadableString() = toMessages().joinToString("\n")

@Composable
fun ZonedDateTime.toReadableDays(dateFormatter: DateTimeFormatter): String {
    val duration = Duration.between(this, ZonedDateTime.now())
    return when (duration.toDays()) {
        -1L -> R.string.tomorrow.string()
        0L -> R.string.today.string()
        1L -> R.string.yesterday.string()
        else -> dateFormatter.format(this)
    }
}
