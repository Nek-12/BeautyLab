package com.nek12.beautylab.ui.screens.home

import androidx.compose.runtime.Immutable
import com.nek12.androidutils.extensions.android.Text
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.core.model.net.mobile.MobileViewResponse
import com.nek12.beautylab.ui.items.BrandChipItem
import com.nek12.beautylab.ui.items.CategoryChipItem
import com.nek12.beautylab.ui.items.ProductCardItem
import com.nek12.beautylab.ui.items.cards
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import java.util.*


@Immutable
sealed class HomeState: MVIState {

    object Loading: HomeState()
    data class Error(val text: Text): HomeState()

    data class DisplayingContent(
        val popularProducts: List<ProductCardItem>,
        val newProducts: List<ProductCardItem>,
        val mostDiscountedProducts: List<ProductCardItem>,
        val categories: List<CategoryChipItem>,
        val brands: List<BrandChipItem>,
        val userName: String,
        val userBalance: Double,
    ): HomeState() {

        constructor(response: MobileViewResponse): this(
            response.popularProducts.cards(),
            response.newProducts.cards(),
            response.mostDiscountedProducts.cards(),
            response.categories.map { CategoryChipItem(it) },
            response.topBrands.map { BrandChipItem(it) },
            response.user.name,
            response.user.bonusBalance,
        )
    }
}

@Immutable
sealed class HomeIntent: MVIIntent {

    data class ClickedProduct(val item: ProductCardItem): HomeIntent()
    data class ClickedCategory(val item: CategoryChipItem): HomeIntent()
    data class ClickedBrand(val item: BrandChipItem): HomeIntent()
    object ClickedRetry: HomeIntent()
    object ClickedLogout: HomeIntent()
    object EnteredHome: HomeIntent()
}

@Immutable
sealed class HomeAction: MVIAction {

    data class GoToProductList(val filters: FiltersPayload): HomeAction()
    object GoToLogIn: HomeAction()
    object GoToProfile: HomeAction()
    data class GoToProductDetails(val id: UUID): HomeAction()
}
