package com.nek12.beautylab.ui.screens.login

import com.nek12.androidutils.extensions.android.Text
import com.nek12.androidutils.extensions.core.fold
import com.nek12.beautylab.R
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.common.input.Input
import com.nek12.beautylab.data.net.AuthManager
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.data.util.ApiError
import com.nek12.beautylab.ui.screens.login.LoginIntent.ClickedLogin
import com.nek12.beautylab.ui.screens.login.LoginIntent.ClickedSignUp
import com.nek12.beautylab.ui.screens.login.LoginIntent.PasswordChanged
import com.nek12.beautylab.ui.screens.login.LoginIntent.UsernameChanged
import com.nek12.beautylab.ui.screens.login.LoginState.AcceptingInput
import com.nek12.beautylab.ui.screens.login.LoginState.Loading
import com.nek12.flowMVI.android.MVIViewModel

class LoginViewModel(
    private val repo: BeautyLabRepo,
    private val authManager: AuthManager,
): MVIViewModel<LoginState, LoginIntent, LoginAction>() {

    override val initialState get() = AcceptingInput()

    init {
        if (authManager.isLoggedIn) {
            send(LoginAction.GoBack)
        }
    }

    private val usernameForm = Form.Username()
    private val passwordForm = Form.Password

    override suspend fun reduce(intent: LoginIntent) = when (intent) {
        is ClickedLogin -> withState<AcceptingInput> {
            val password = passwordForm.validate(password.value)
            val username = usernameForm.validate(username.value)

            if (username.isValid && password.isValid) {
                launchLogin(username.value, password.value)
                Loading
            } else {
                AcceptingInput(username, password)
            }
        }
        is PasswordChanged -> withState<AcceptingInput> {
            copy(password = passwordForm.validate(intent.value))
        }
        is UsernameChanged -> withState<AcceptingInput> {
            copy(username = usernameForm.validate(intent.value))
        }
        ClickedSignUp -> {
            send(LoginAction.GoToSignUp)
            currentState
        }
    }

    private fun launchLogin(username: String, password: String) = launchForState {
        repo.logIn(username, password).fold(
            onSuccess = { send(LoginAction.GoBack) },
            onError = {
                send(
                    LoginAction.ShowSnackbar(
                        if (it is ApiError.NotFound) {
                            Text.Resource(R.string.invalid_credentials)
                        } else it.genericMessage
                    )
                )
            }
        )

        AcceptingInput(
            Input.Empty(username),
            Input.Empty(password),
        )
    }
}
