package com.nek12.beautylab.ui.screens.login

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeAction.Companion
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.common.snackbar
import com.nek12.beautylab.ui.screens.destinations.SignUpScreenDestination
import com.nek12.beautylab.ui.screens.login.LoginAction.GoBack
import com.nek12.beautylab.ui.screens.login.LoginAction.GoToSignUp
import com.nek12.beautylab.ui.screens.login.LoginAction.ShowSnackbar
import com.nek12.beautylab.ui.screens.login.LoginIntent.ClickedLogin
import com.nek12.beautylab.ui.screens.login.LoginIntent.ClickedSignUp
import com.nek12.beautylab.ui.screens.login.LoginIntent.PasswordChanged
import com.nek12.beautylab.ui.screens.login.LoginIntent.UsernameChanged
import com.nek12.beautylab.ui.screens.login.LoginState.AcceptingInput
import com.nek12.beautylab.ui.screens.login.LoginState.Loading
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLTextInput
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(provider = getViewModel<LoginViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
            is GoToSignUp -> navigator.navigate(SignUpScreenDestination())
            is ShowSnackbar -> snackbar(action.text.string(context), scaffoldState, SnackbarDuration.Long)
        }
    }

    BackHandler {
        (context as? Activity)?.finish()
    }

    LoginScreenContent(state, scaffoldState)

}

@Composable
fun MVIIntentScope<LoginIntent, LoginAction>.LoginScreenContent(state: LoginState, scaffoldState: ScaffoldState) {
    Scaffold(
        topBar = { BLTopBar(R.string.app_name) },
        scaffoldState = scaffoldState,
    ) { padding ->
        when (state) {
            is AcceptingInput -> {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(bottom = 72.dp, start = 12.dp, end = 12.dp)
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = 300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BLIcon(asset = GMRIcon.gmr_spa, size = 64.dp)
                    }
                    BLTextInput(
                        input = state.username,
                        onTextChange = { send(UsernameChanged(it)) },
                        lengthRange = Form.Username.DEFAULT_LENGTH_RANGE,
                        label = R.string.username.string(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Ascii,
                            imeAction = ImeAction.Next,
                        ),
                    )
                    BLTextInput(
                        input = state.password,
                        onTextChange = { send(PasswordChanged(it)) },
                        lengthRange = Form.Password.LENGTH_RANGE,
                        label = R.string.password.string(),
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false, keyboardType = KeyboardType.Password, imeAction = Companion.Go,
                        ),
                        keyboardActions = KeyboardActions { send(ClickedLogin) }
                    )
                    Row(
                        Modifier
                            .padding(vertical = 32.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button({ send(ClickedSignUp) }) {
                            Text(R.string.sign_up.string())
                        }

                        Button({ send(ClickedLogin) }) {
                            Text(R.string.log_in.string())
                        }
                    }
                }
            }
            Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview(name = "LoginScreen", showSystemUi = false, showBackground = true)
private fun LoginScreenPreview() = ScreenPreview {
    LoginScreenContent(AcceptingInput(), rememberScaffoldState())
}
