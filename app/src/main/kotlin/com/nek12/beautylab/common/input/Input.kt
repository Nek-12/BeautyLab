package com.nek12.beautylab.common.input

import androidx.compose.runtime.Stable
import arrow.core.Nel
import com.nek12.androidutils.extensions.core.isValid

@Stable
sealed class Input {

    abstract val value: String

    data class Invalid(override val value: String, val errors: Nel<ValidationError>) : Input()
    data class Empty(override val value: String = "") : Input()
    data class Valid(override val value: String) : Input()

    fun orNull(): String? = if (this is Valid) value else null

    fun orEmpty(): String = orNull() ?: ""

    val isValid get() = this is Valid

    val isInvalid get() = this is Invalid

    val isValidOrEmpty get() = this is Valid || this is Empty

    val isEmptyValue get() = !this.value.isValid
}
