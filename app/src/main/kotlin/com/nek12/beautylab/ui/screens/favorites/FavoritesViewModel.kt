package com.nek12.beautylab.ui.screens.favorites

import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoToAboutApp
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.favorites.FavoritesIntent.ClickedInfo
import com.nek12.beautylab.ui.screens.favorites.FavoritesIntent.ClickedProduct
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.DisplayingFavorites
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Empty
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Error
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Loading
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState

class FavoritesViewModel(
    private val repo: BeautyLabRepo,
): MVIViewModel<FavoritesState, FavoritesIntent, FavoritesAction>(Loading) {

    override fun recover(from: Exception) = Error(from)

    init {
        loadFavorites()
    }

    override suspend fun reduce(intent: FavoritesIntent): FavoritesState = when (intent) {
        is ClickedProduct -> {
            send(GoToProductDetails(intent.item.id))
            currentState
        }
        is ClickedInfo -> {
            send(GoToAboutApp)
            currentState
        }
    }

    private fun loadFavorites() = launchForState {
        val favs = repo.getFavorites().orThrow()

        if (favs.isEmpty()) {
            Empty
        } else {
            DisplayingFavorites(favs)
        }
    }
}
