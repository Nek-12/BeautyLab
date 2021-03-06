package com.nek12.beautylab.ui.screens.product.filters

import com.nek12.androidutils.extensions.core.isValid
import com.nek12.androidutils.extensions.core.orThrow
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.input.Form
import com.nek12.beautylab.data.repo.BeautyLabRepo
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ChangedDiscount
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ChangedMaxPrice
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ChangedMinAmountAvailable
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ChangedMinPrice
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ClickedBrand
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ClickedCategory
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ClickedOk
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ClickedReset
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.ClickedSelectDateCreated
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.SelectedDateCreated
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.SelectedSort
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersIntent.SwitchedAscending
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.Error
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.Loading
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.SelectingFilters
import com.nek12.flowMVI.android.MVIViewModel
import com.nek12.flowMVI.currentState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.time.ZoneOffset

class ProductFiltersViewModel(
    private val initialFilters: FiltersPayload?,
    private val repo: BeautyLabRepo,
): MVIViewModel<ProductFiltersState, ProductFiltersIntent, ProductFiltersAction>(Loading) {

    override fun recover(from: Exception) = Error(from)

    init {
        launchLoadData()
    }

    override suspend fun reduce(intent: ProductFiltersIntent): ProductFiltersState = when (intent) {
        is ChangedDiscount -> withState<SelectingFilters> {
            copy(discountRange = intent.discount)
        }
        is ChangedMinPrice -> withState<SelectingFilters> {
            copy(minPrice = Form.Number.validate(intent.input))
        }
        is ChangedMaxPrice -> withState<SelectingFilters> {
            copy(maxPrice = Form.Number.validate(intent.input))
        }
        is ChangedMinAmountAvailable -> withState<SelectingFilters> {
            copy(minAmountAvailable = intent.amount)
        }
        is ClickedBrand -> withState<SelectingFilters> {
            copy(selectedBrandIndex = brands.indexOf(intent.item).takeIf { it != selectedBrandIndex })
        }
        is ClickedCategory -> withState<SelectingFilters> {
            copy(selectedCategoryIndex = categories.indexOf(intent.item).takeIf { it != selectedCategoryIndex })
        }
        is ClickedSelectDateCreated -> withState<SelectingFilters> {
            send(ProductFiltersAction.OpenDateCreatedPicker)
            currentState
        }
        is SelectedDateCreated -> withState<SelectingFilters> {
            //TODO: check if this is a valid conversion
            copy(createdAfter = intent.date.atStartOfDay().toInstant(ZoneOffset.UTC))
        }
        is SelectedSort -> withState<SelectingFilters> {
            copy(sort = intent.sort)
        }
        is SwitchedAscending -> withState<SelectingFilters> {
            copy(isAscending = intent.isAscending)
        }
        is ClickedOk -> withState<SelectingFilters> {
            val minPrice = Form.Number.validate(minPrice.value)
            val maxPrice = maxPrice.value.takeIf { it.isValid }?.let { Form.Number.validate(it) }

            if (minPrice.isValid && maxPrice?.isValid != false) {
                send(ProductFiltersAction.GoBack(this.payload(minPrice.value.toDouble(), maxPrice?.value?.toDouble())))
            }

            currentState
        }
        is ClickedReset -> withState<SelectingFilters> {
            send(ProductFiltersAction.GoBack(FiltersPayload()))
            currentState
        }
    }

    private fun launchLoadData() = launchForState(Dispatchers.IO) {
        val brandsResult = async { repo.getBrands() }
        val categoriesResult = async { repo.getCategories() }

        val brands = brandsResult.await().orThrow()
        val categories = categoriesResult.await().orThrow()

        SelectingFilters(initialFilters ?: FiltersPayload(), brands, categories)
    }
}
