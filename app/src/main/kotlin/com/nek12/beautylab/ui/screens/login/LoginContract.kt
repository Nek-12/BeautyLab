package com.nek12.beautylab.ui.screens.login

import com.nek12.beautylab.common.Text
import com.nek12.beautylab.common.input.Input
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

sealed class LoginState : MVIState {
    object Loading : LoginState()
    data class AcceptingInput(val username: Input = Input.Empty(), val password: Input = Input.Empty()) : LoginState()
}


sealed class LoginAction : MVIAction {
    data class ShowSnackbar(val text: Text) : LoginAction()
    object GoToMain : LoginAction()
    object GoToSignUp : LoginAction()
}

sealed class LoginIntent : MVIIntent {
    data class UsernameChanged(val value: String) : LoginIntent()
    data class PasswordChanged(val value: String) : LoginIntent()
    object OkClicked : LoginIntent()
    object SignUpClicked : LoginIntent()
}
