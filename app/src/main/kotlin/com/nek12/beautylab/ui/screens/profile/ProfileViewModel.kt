package com.nek12.beautylab.ui.screens.profile

import com.nek12.beautylab.ui.screens.profile.ProfileState.Error
import com.nek12.beautylab.ui.screens.profile.ProfileState.Loading
import com.nek12.flowMVI.android.MVIViewModel

class ProfileViewModel: MVIViewModel<ProfileState, ProfileIntent, ProfileAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: ProfileIntent): ProfileState = when (intent) {
        else -> TODO()
    }
}
