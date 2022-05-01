package com.nek12.beautylab.ui.screens.login

import androidx.compose.runtime.Immutable
import com.nek12.androidutils.extensions.android.Text
import com.nek12.beautylab.common.input.Input
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class LoginState: MVIState {

    object Loading: LoginState()
    data class AcceptingInput(val username: Input = Input.Empty(), val password: Input = Input.Empty()): LoginState()
}

@Immutable
sealed class LoginAction: MVIAction {

    data class ShowSnackbar(val text: Text): LoginAction()
    object GoBack: LoginAction()
    object GoToSignUp: LoginAction()
}

@Immutable
sealed class LoginIntent: MVIIntent {

    data class UsernameChanged(val value: String): LoginIntent()
    data class PasswordChanged(val value: String): LoginIntent()
    object OkClicked: LoginIntent()
    object SignUpClicked: LoginIntent()
}
