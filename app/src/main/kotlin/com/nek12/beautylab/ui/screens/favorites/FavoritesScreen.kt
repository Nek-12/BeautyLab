package com.nek12.beautylab.ui.screens.favorites

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.screens.favorites.FavoritesAction.GoBack
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Empty
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Error
import com.nek12.beautylab.ui.screens.favorites.FavoritesState.Loading
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun FavoritesScreen(
        navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<FavoritesViewModel>()) { state ->

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
        }
    }

    FavoritesContent(state)
}

@Composable
private fun MVIIntentScope<FavoritesIntent, FavoritesAction>.FavoritesContent(state: FavoritesState) {
    when (state) {
        is Empty -> BLEmptyView()
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
    }
}

@Composable
@Preview(name = "Favorites", showSystemUi = true, showBackground = true)
private fun FavoritesPreview() = ScreenPreview {
    Column {
        FavoritesContent(state = Empty)
        FavoritesContent(state = Error(Exception()))
    }
}
