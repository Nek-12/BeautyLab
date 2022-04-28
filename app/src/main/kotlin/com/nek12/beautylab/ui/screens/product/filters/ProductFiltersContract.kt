package com.nek12.beautylab.ui.screens.product.filters

import androidx.compose.runtime.Immutable
import com.nek12.androidutils.extensions.core.indexOfFirstOrNull
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.input.Input
import com.nek12.beautylab.core.model.net.SortDirection
import com.nek12.beautylab.core.model.net.brand.BrandResponse
import com.nek12.beautylab.core.model.net.product.ProductSort
import com.nek12.beautylab.core.model.net.product.category.ProductCategoryResponse
import com.nek12.beautylab.ui.items.BrandChipItem
import com.nek12.beautylab.ui.items.CategoryChipItem
import com.nek12.flowMVI.MVIAction
import com.nek12.flowMVI.MVIIntent
import com.nek12.flowMVI.MVIState
import java.time.Instant
import java.time.LocalDate

@Immutable
sealed class ProductFiltersState: MVIState {

    data class Error(val e: Throwable?): ProductFiltersState()
    object Loading: ProductFiltersState()
    data class SelectingFilters(
        val brands: List<BrandChipItem>,
        val categories: List<CategoryChipItem>,
        val selectedBrandIndex: Int? = null,
        val selectedCategoryIndex: Int? = null,
        val sort: ProductSort = ProductSort.Name,
        val isAscending: Boolean = false,
        val discountRange: ClosedFloatingPointRange<Float> = 0f..1f,
        val minPrice: Input = Input.Empty("0"),
        val maxPrice: Input = Input.Empty(),
        val createdAfter: Instant? = null,
        val minAmountAvailable: Float = 0f,
    ): ProductFiltersState() {

        val selectedBrand get() = selectedBrandIndex?.let { brands[it] }
        val selectedCategory get() = selectedCategoryIndex?.let { categories[it] }

        val discountFrom: Int get() = (discountRange.start * 100).toInt()
        val discountTo: Int get() = (discountRange.endInclusive * 100).toInt()

        fun payload(minPrice: Double, maxPrice: Double?) = FiltersPayload(
            sort = sort,
            direction = if (isAscending) SortDirection.Asc else SortDirection.Desc,
            brandId = selectedBrand?.id,
            categoryId = selectedCategory?.id,
            minDiscount = discountRange.start,
            maxDiscount = discountRange.endInclusive,
            minPrice = minPrice,
            maxPrice = maxPrice,
            createdAfter = createdAfter,
            minAmountAvailable = minAmountAvailable.toLong()
        )

        constructor(
            filters: FiltersPayload,
            brands: List<BrandResponse>,
            categories: List<ProductCategoryResponse>,
        ): this(
            brands.map { BrandChipItem(it) },
            categories.map { CategoryChipItem(it) },
            brands.indexOfFirstOrNull { it.id == filters.brandId },
            categories.indexOfFirstOrNull { it.id == filters.categoryId },
            filters.sort,
            filters.direction == SortDirection.Asc,
            filters.minDiscount..filters.maxDiscount,
            Input.Empty(filters.minPrice.toInt().toString()),
            Input.Empty(filters.maxPrice?.toInt()?.toString() ?: ""),
            filters.createdAfter,
            filters.minAmountAvailable.toFloat().coerceAtMost(MAX_PRODUCT_AMOUNT_AVAILABLE)
        )

    }

    companion object {

        const val MAX_PRODUCT_AMOUNT_AVAILABLE = 1000f
        val PRODUCT_AMOUNT_RANGE get() = 0f..MAX_PRODUCT_AMOUNT_AVAILABLE
    }
}

@Immutable
sealed class ProductFiltersIntent: MVIIntent {

    data class ClickedBrand(val item: BrandChipItem): ProductFiltersIntent()
    data class ClickedCategory(val item: CategoryChipItem): ProductFiltersIntent()
    data class SelectedSort(val sort: ProductSort): ProductFiltersIntent()
    data class SwitchedAscending(val isAscending: Boolean): ProductFiltersIntent()
    data class ChangedDiscount(val discount: ClosedFloatingPointRange<Float>): ProductFiltersIntent()
    data class ChangedMinPrice(val input: String): ProductFiltersIntent()
    data class ChangedMaxPrice(val input: String): ProductFiltersIntent()
    object ClickedSelectDateCreated: ProductFiltersIntent()
    data class SelectedDateCreated(val date: LocalDate): ProductFiltersIntent()
    data class ChangedMinAmountAvailable(val amount: Float): ProductFiltersIntent()
    object ClickedOk: ProductFiltersIntent()
    object ClickedReset: ProductFiltersIntent()
}

@Immutable
sealed class ProductFiltersAction: MVIAction {

    data class GoBack(val filters: FiltersPayload): ProductFiltersAction()
    object OpenDateCreatedPicker: ProductFiltersAction()
}
