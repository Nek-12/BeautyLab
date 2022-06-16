package com.nek12.beautylab.ui.screens.signup

import arrow.core.nel
import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.R
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.common.input.Input
import com.nek12.beautylab.common.input.Input.Empty
import com.nek12.beautylab.common.input.Input.Invalid
import com.nek12.beautylab.common.input.ValidationError
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.signup.SignUpAction.GoBack
import com.nek12.beautylab.ui.screens.signup.SignUpAction.GoToHome
import com.nek12.beautylab.ui.screens.signup.SignUpAction.ShowSnackbar
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.ClickedSignUp
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.GoBackClicked
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.NameChanged
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.PasswordChanged
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.PasswordConfirmationChanged
import com.nek12.beautylab.ui.screens.signup.SignUpIntent.UsernameChanged
import com.nek12.beautylab.ui.screens.signup.SignUpState.AcceptingInput
import com.nek12.beautylab.ui.screens.signup.SignUpState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState

class SignUpViewModel(
    private val repo: BeautyLabRepo,
): MVIViewModel<SignUpState, SignUpIntent, SignUpAction>(AcceptingInput()) {

    private val usernameForm = Form.Username()
    private val nameForm = Form.Name()
    private val passwordForm = Form.Password

    override suspend fun reduce(intent: SignUpIntent) = when (intent) {
        is ClickedSignUp -> withState<AcceptingInput> {
            val password = passwordForm.validate(password.value)
            val username = usernameForm.validate(username.value)
            val name = nameForm.validate(name.value)
            val confirmation = validateConfirmation(password, passwordConfirmation.value)

            if (username.isValid && password.isValid && name.isValid && confirmation.isValid) {
                launchSignUp(username.value, name.value, password.value)
                Loading
            } else {
                AcceptingInput(username, name, password, confirmation)
            }
        }
        is PasswordChanged -> withState<AcceptingInput> {
            copy(password = passwordForm.validate(intent.value))
        }
        is UsernameChanged -> withState<AcceptingInput> {
            copy(username = usernameForm.validate(intent.value))
        }
        is NameChanged -> withState<AcceptingInput> {
            copy(name = nameForm.validate(intent.value))
        }
        GoBackClicked -> {
            send(GoBack)
            currentState
        }
        is PasswordConfirmationChanged -> withState<AcceptingInput> {
            copy(passwordConfirmation = validateConfirmation(password, intent.value))
        }
    }

    private fun validateConfirmation(password: Input, confirmation: String): Input {
        return if (password.value != confirmation) {
            Invalid(confirmation, ValidationError.IsNotEqualTo(R.string.password).nel())
        } else {
            passwordForm.validate(confirmation)
        }
    }

    private fun launchSignUp(username: String, name: String, password: String) = launchForState {
        repo.signUp(username, name, password).fold(
            onSuccess = { send(GoToHome) },
            onError = { send(ShowSnackbar(it.genericMessage)) }
        )
        AcceptingInput(
            Empty(username),
            Empty(name),
            Empty(password),
            Empty(password)
        )
    }
}
