package com.nek12.beautylab.common

import androidx.compose.runtime.Composable
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.input.ValidationError
import com.nek12.beautylab.common.input.toMessages
import java.time.Duration
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

inline val <reified T : Throwable?> T.genericMessage: String
    @Composable inline get() = when (this) {
        //TODO
        null -> R.string.unknown_error_template.string(R.string.no_message)
        else -> localizedMessage ?: message ?: R.string.unknown_error_template.string(toString())
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
