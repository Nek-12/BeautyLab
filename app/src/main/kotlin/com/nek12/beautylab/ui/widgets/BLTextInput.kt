package com.nek12.beautylab.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.common.input.Input
import com.nek12.beautylab.common.input.Strategy
import com.nek12.beautylab.common.toReadableString

@Composable
fun BLTextInput(
    input: Input,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    lengthRange: IntRange? = null,
    label: String? = null,
    placeholder: String? = null,
    hint: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {

    Column(modifier) {
        OutlinedTextField(
            value = input.value,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            label = { label?.let { Text(label, style = MaterialTheme.typography.caption) } },
            placeholder = { placeholder?.let { Text(placeholder, style = MaterialTheme.typography.caption) } },
            singleLine = singleLine,
            isError = input is Input.Invalid,
            readOnly = readOnly,
            colors = colors,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
        )
        Row(
            modifier = modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            when {
                input is Input.Invalid -> {
                    Text(
                        text = input.errors.toReadableString(),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.error,
                    )
                }
                hint != null -> {
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.caption,
                    )
                }

            }
            lengthRange?.let {
                Text(
                    text = "${input.value.length}/${it.last}",
                    color = if (input is Input.Invalid) MaterialTheme.colors.error else Color.Unspecified,
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
@Preview(name = "BLTextInput", widthDp = 360, showSystemUi = false, showBackground = true)
private fun BLTextInputPreview() {
    Column {
        BLTextInput(
            Form.Email.validate("bl", Strategy.LazyEvaluation),
            {},
            lengthRange = 3..55,
            label = "blah",
            placeholder = "SomePlaceHolder",
        )

        BLTextInput(
            Form.Title().validate("Valid", Strategy.LazyEvaluation),
            {},
            lengthRange = 3..55,
            label = "blah",
            placeholder = "SomePlaceHolder",
            hint = "Hint"
        )

        BLTextInput(
            Input.Empty(""),
            {},
            lengthRange = 3..55,
            label = "blah",
            placeholder = "SomePlaceHolder",
        )
    }
}
