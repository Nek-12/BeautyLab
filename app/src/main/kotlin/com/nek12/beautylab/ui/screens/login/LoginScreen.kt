package com.nek12.beautylab.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.common.snackbar
import com.nek12.beautylab.ui.screens.destinations.HomeScreenDestination
import com.nek12.beautylab.ui.screens.destinations.SignUpScreenDestination
import com.nek12.beautylab.ui.screens.login.LoginAction.*
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLTextInput
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@RootNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(provider = getViewModel<LoginViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    consume { action ->
        when (action) {
            is GoToMain -> navigator.navigate(HomeScreenDestination())
            GoToSignUp -> navigator.navigate(SignUpScreenDestination())
            is ShowSnackbar -> snackbar(action.text.string(context), scaffoldState, SnackbarDuration.Long)
        }
    }

    Scaffold(
        topBar = { BLTopBar(R.string.app_name) },
        scaffoldState = scaffoldState,
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(padding)) {
            when (state) {
                is LoginState.AcceptingInput -> {
                    Box(Modifier
                        .fillMaxWidth()
                        .heightIn(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BLIcon(asset = GMRIcon.gmr_spa)
                    }
                    BLTextInput(
                        input = state.username,
                        onTextChange = { send(LoginIntent.UsernameChanged(it)) },
                        lengthRange = Form.Username.DEFAULT_LENGTH_RANGE,
                        label = R.string.username.string(),
                    )
                    BLTextInput(
                        input = state.password,
                        onTextChange = { send(LoginIntent.PasswordChanged(it)) },
                        lengthRange = Form.Password.LENGTH_RANGE,
                        label = R.string.password.string(),
                    )
                    Row(Modifier
                        .padding(vertical = 32.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround) {
                        Button({ send(LoginIntent.SignUpClicked) }) {
                            Text(R.string.sign_up.string())
                        }

                        Button({ send(LoginIntent.OkClicked) }) {
                            Text(R.string.log_in.string())
                        }
                    }
                }
                LoginState.Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}


@Composable
@Preview(name = "LoginScreen", showSystemUi = false, showBackground = true)
private fun LoginScreenPreview() {
}
