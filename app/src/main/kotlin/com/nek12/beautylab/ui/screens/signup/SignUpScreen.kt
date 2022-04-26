package com.nek12.beautylab.ui.screens.signup

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
import com.nek12.beautylab.ui.screens.signup.SignUpAction.*
import com.nek12.beautylab.ui.screens.signup.SignUpState.AcceptingInput
import com.nek12.beautylab.ui.screens.signup.SignUpState.Loading
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLTextInput
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel


@Destination
@Composable
fun SignUpScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(provider = getViewModel<SignUpViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    consume { action ->
        when (action) {
            is GoToMain -> navigator.navigate(HomeScreenDestination()) {
                popUpTo(HomeScreenDestination.route) {
                    inclusive = true
                }
            }
            is ShowSnackbar -> snackbar(action.text.string(context), scaffoldState, SnackbarDuration.Long)
            GoBack -> navigator.navigateUp()
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
                is AcceptingInput -> {
                    Box(Modifier
                        .fillMaxWidth()
                        .heightIn(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        BLIcon(asset = GMRIcon.gmr_spa, size = 64.dp)
                    }
                    BLTextInput(
                        input = state.username,
                        onTextChange = { send(SignUpIntent.UsernameChanged(it)) },
                        lengthRange = Form.Username.DEFAULT_LENGTH_RANGE,
                        label = R.string.username.string(),
                    )
                    BLTextInput(
                        input = state.name,
                        onTextChange = { send(SignUpIntent.NameChanged(it)) },
                        lengthRange = Form.Name.DEFAULT_LENGTH_RANGE,
                        label = R.string.your_name.string(),
                    )
                    BLTextInput(
                        input = state.password,
                        onTextChange = { send(SignUpIntent.PasswordChanged(it)) },
                        lengthRange = Form.Password.LENGTH_RANGE,
                        label = R.string.password.string(),
                    )
                    BLTextInput(
                        input = state.passwordConfirmation,
                        onTextChange = { send(SignUpIntent.PasswordConfirmationChanged(it)) },
                        lengthRange = Form.Password.LENGTH_RANGE,
                        label = R.string.password.string(),
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        Arrangement.Center,
                        Alignment.CenterVertically
                    ) {
                        Button({ send(SignUpIntent.OkClicked) }) {
                            Text(R.string.sign_up.string())
                        }
                    }
                }
                Loading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
