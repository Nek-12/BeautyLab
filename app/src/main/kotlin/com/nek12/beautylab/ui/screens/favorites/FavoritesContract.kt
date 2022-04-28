package com.nek12.beautylab.ui.screens.favorites

import androidx.compose.runtime.Immutable
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState

@Immutable
sealed class FavoritesState: MVIState {

    object Loading: FavoritesState()
    object Empty: FavoritesState()
    data class Error(val e: Throwable?): FavoritesState()
}

@Immutable
sealed class FavoritesIntent: MVIIntent

@Immutable
sealed class FavoritesAction: MVIAction {

    object GoBack: FavoritesAction()
}
