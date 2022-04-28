package com.nek12.beautylab.ui.screens.signup

import com.nek12.beautylab.common.Text
import com.nek12.beautylab.common.input.Input
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

sealed class SignUpState: MVIState {
    object Loading: SignUpState()
    data class AcceptingInput(
        val username: Input = Input.Empty(),
        val name: Input = Input.Empty(),
        val password: Input = Input.Empty(),
        val passwordConfirmation: Input = Input.Empty(),
    ): SignUpState()
}


sealed class SignUpAction: MVIAction {
    data class ShowSnackbar(val text: Text): SignUpAction()
    object GoBack: SignUpAction()
}

sealed class SignUpIntent: MVIIntent {
    data class UsernameChanged(val value: String): SignUpIntent()
    data class PasswordChanged(val value: String): SignUpIntent()
    data class PasswordConfirmationChanged(val value: String): SignUpIntent()
    data class NameChanged(val value: String): SignUpIntent()
    object OkClicked: SignUpIntent()
    object GoBackClicked: SignUpIntent()
}
