package com.nek12.beautylab.ui.screens.favorites

import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Error
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Loading
import com.nek12.flowMVI.android.MVIViewModel

class FavoritesViewModel: MVIViewModel<FavoritesState, FavoritesIntent, FavoritesAction>() {

    override val initialState get() = Loading
    override fun recover(from: Exception) = Error(from)

    override suspend fun reduce(intent: FavoritesIntent): FavoritesState = when (intent) {
        else -> TODO()
    }
}
