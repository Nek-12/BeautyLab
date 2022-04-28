package com.nek12.beautylab.ui.screens.profile

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class ProfileState: MVIState {

    object Loading: ProfileState()
    object Empty: ProfileState()
    data class Error(val e: Throwable?): ProfileState()
}

@Immutable
sealed class ProfileIntent: MVIIntent

@Immutable
sealed class ProfileAction: MVIAction {

    object GoBack: ProfileAction()
}
