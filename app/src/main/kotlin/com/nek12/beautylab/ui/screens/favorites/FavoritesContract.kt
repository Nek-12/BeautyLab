package com.nek12.beautylab.ui.screens.favorites

import androidx.compose.runtime.Immutable
import com.nek12.beautylab.core.model.net.favorite.FavoriteItemResponse
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import java.util.*

@Immutable
sealed class FavoritesState: MVIState {

    object Loading: FavoritesState()
    object Empty: FavoritesState()
    data class Error(val e: Throwable?): FavoritesState()

    data class DisplayingFavorites(
        val favorites: List<ProductCardItem>,
    ): FavoritesState() {

        companion object {

            //workaround declaration clash
            operator fun invoke(response: List<FavoriteItemResponse>) =
                DisplayingFavorites(response.map { ProductCardItem(it.product) })
        }
    }
}

@Immutable
sealed class FavoritesIntent: MVIIntent {

    data class ClickedProduct(val item: ProductCardItem): FavoritesIntent()
}

@Immutable
sealed class FavoritesAction: MVIAction {

    data class GoToProductDetails(val id: UUID): FavoritesAction()
    object GoBack: FavoritesAction()
}
