package com.nek12.beautylab.common.input

import android.util.Patterns
import com.nek12.beautylab.R
import com.nek12.beautylab.common.input.Rules.*

sealed class Form(
    private vararg val rules: Rule,
) {

    fun validate(input: String, strategy: Strategy = Strategy.FailFast): Input =
        rules(input.trim(), strategy).fold(
            ifLeft = { Input.Invalid(input, it) },
            ifRight = { Input.Valid(it) },
        )

    object Email : Form(
        NonEmpty,
        LengthRange(1..250),
        Matches(Patterns.EMAIL_ADDRESS, R.string.email),
    )

    data class Title(val length: IntRange = DEFAULT_LENGTH_RANGE) : Form(
        NonEmpty,
        LengthRange(length),
        NoDigits,
    ) {

        companion object {

            val DEFAULT_LENGTH_RANGE = 3..40
        }
    }

    data class Description(val range: IntRange = DEFAULT_LENGTH_RANGE) : Form(
        LengthRange(range),
    ) {

        companion object {

            val DEFAULT_LENGTH_RANGE = 0..256
        }
    }

    object Time : Form(TimeOnly)

    data class Search(
        val lengthRange: IntRange = DEFAULT_LENGTH_RANGE,
    ) : Form(
        NonEmpty,
        LengthRange(lengthRange),
        DigitsAndLettersOnly,
    ) {

        companion object {

            val DEFAULT_LENGTH_RANGE = 3..32
        }
    }

    data class Username(
        val lengthRange: IntRange = DEFAULT_LENGTH_RANGE,
    ) : Form(
        NonEmpty,
        LengthRange(lengthRange),
        DigitsAndLettersOnly,
    ) {

        companion object {

            val DEFAULT_LENGTH_RANGE = 4..20
        }
    }

    object Password : Form(
        NonEmpty,
        Regex("^[a-zA-Z0-9_.,!@#$%^&*]{4,32}$", R.string.password)
    ) {

        val LENGTH_RANGE = 4..32
    }

    object Number : Form(NonEmpty, DigitsOnly)

    data class Name(
        val lengthRange: IntRange = DEFAULT_LENGTH_RANGE,
    ) : Form(
        NonEmpty,
        LengthRange(lengthRange),
    ) {

        companion object {

            val DEFAULT_LENGTH_RANGE = 3..32
        }
    }
}
