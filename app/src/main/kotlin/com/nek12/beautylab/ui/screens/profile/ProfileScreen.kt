package com.nek12.beautylab.ui.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.profile.ProfileAction.*
import com.nek12.beautylab.ui.screens.profile.ProfileState.*
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun ProfileScreen(
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<ProfileViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    ProfileContent(state)
}

@Composable
private fun MVIIntentScope<ProfileIntent, ProfileAction>.ProfileContent(state: ProfileState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "Profile", showSystemUi = true, showBackground = true)
private fun ProfilePreview() = ScreenPreview {
    Column {
        ProfileContent(state = Empty)
        ProfileContent(state = Error(Exception()))
    }
}
