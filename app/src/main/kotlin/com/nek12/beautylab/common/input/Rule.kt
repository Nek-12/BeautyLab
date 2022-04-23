package com.nek12.beautylab.common.input

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import arrow.core.*
import arrow.core.computations.either
import arrow.typeclasses.Monoid
import arrow.typeclasses.Semigroup
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.core.Time
import com.nek12.androidutils.extensions.core.isValid
import com.nek12.androidutils.extensions.core.isValidPattern
import com.nek12.androidutils.extensions.core.spans
import com.nek12.beautylab.R
import com.nek12.beautylab.common.input.Strategy.FailFast
import com.nek12.beautylab.common.input.Strategy.LazyEvaluation
import com.nek12.beautylab.common.input.ValidationError.*
import java.util.regex.Pattern

sealed interface Strategy {
    object FailFast : Strategy
    object LazyEvaluation : Strategy
}

fun interface Rule {

    operator fun invoke(value: String): ValidatedNel<ValidationError, String>
}

sealed class Rules {
    object NonEmpty : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> =
            if (value.isValid) value.validNel() else Blank.invalidNel()
    }

    data class Contains(val needle: String) : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.contains(needle)) value.validNel() else DoesNotContain(needle).invalidNel()
        }
    }

    data class LengthRange(val range: IntRange) : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value spans range) value.validNel() else NotInRange(range).invalidNel()
        }
    }

    /**
     * @param identityRepresentation a string id describing what given [pattern] describes: an email, phone number etc.
     */
    data class Matches(val pattern: Pattern, @StringRes val identityRepresentation: Int) : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.isValidPattern(pattern)) value.validNel() else IsNot(
                identityRepresentation,
            ).invalidNel()
        }
    }

    object NoDigits : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.none { it.isDigit() }) value.validNel() else ContainsDigits.invalidNel()
        }
    }

    object NoLetters : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.none { it.isLetter() }) value.validNel() else ContainsLetters.invalidNel()
        }
    }

    object DigitsAndLettersOnly : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.none { !it.isLetterOrDigit() }) value.validNel() else NotAlphaNumeric.invalidNel()
        }
    }

    object TimeOnly : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (kotlin.runCatching { Time.of(value) }.isSuccess) value.validNel() else NotTime.invalidNel()
        }
    }

    data class StartsWith(val prefix: String, val ignoreCase: Boolean = false) : Rule {

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.startsWith(prefix, ignoreCase)) value.validNel()
            else DoesNotStartWith(prefix).invalidNel()
        }
    }

    data class Regex(val regex: kotlin.text.Regex, val representationStringRes: Int) : Rule {

        constructor(regex: String, representationStringRes: Int) : this(regex.toRegex(), representationStringRes)

        override fun invoke(value: String): ValidatedNel<ValidationError, String> {
            return if (value.matches(regex)) value.validNel() else IsNot(representationStringRes).invalidNel()
        }
    }
}

operator fun Array<out Rule>.invoke(input: String, strategy: Strategy): Either<Nel<ValidationError>, String> {
    return when (strategy) {
        FailFast -> {
            either.eager {
                map { it(input).bind() }
                input
            }
        }
        LazyEvaluation -> {
            map { it(input) }
                .combineAll(Monoid.validated(Semigroup.nonEmptyList(), Monoid.string()))
                .map { input }
                .toEither()
        }
    }
}

sealed class ValidationError {

    abstract val message: String @Composable get

    data class DoesNotContain(val value: String) : ValidationError() {

        override val message @Composable get() = R.string.did_not_contain_error_template.string(value)
    }

    data class NotInRange(val range: IntRange) : ValidationError() {

        override val message: String
            @Composable get() = R.string.max_length_error_template.string(
                range.first,
                range.last,
            )
    }

    sealed class Generic(@StringRes val identityString: Int) : ValidationError() {

        override val message: String @Composable get() = identityString.string()
    }

    object Blank : Generic(R.string.empty_input_error)

    object NotAlphaNumeric : Generic(R.string.alphanumeric_error)

    object NotTime : Generic(R.string.not_time_error)

    open class IsNot(@StringRes val patternRepresentation: Int) : ValidationError() {

        override val message
            @Composable get() = R.string.value_is_not_error.string(
                patternRepresentation.string(),
            )
    }

    sealed class ContainsClass(@StringRes val classRepr: Int) : ValidationError() {

        override val message: String
            @Composable get() = R.string.contains_character_type_error.string(
                classRepr.string(),
            )
    }

    object ContainsDigits : ContainsClass(R.string.digits)

    object ContainsLetters : ContainsClass(R.string.letters)

    data class DoesNotStartWith(val value: String) : ValidationError() {

        override val message: String @Composable get() = R.string.start_with_error.string(value)
    }

    data class IsNotEqualTo(val otherFieldName: Int) : ValidationError() {

        override val message: String @Composable get() = R.string.does_not_match_field_template.string(otherFieldName.string())
    }
}

@Composable
fun List<ValidationError>.toMessages() = map { it.message }
