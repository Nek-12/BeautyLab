package com.nek12.beautylab.ui.screens.product.filters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.FiltersPayload
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.common.representation
import com.nek12.beautylab.core.model.net.product.ProductSort
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersAction.GoBack
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersAction.OpenDateCreatedPicker
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.Error
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.Loading
import com.nek12.beautylab.ui.screens.product.filters.ProductFiltersState.SelectingFilters
import com.nek12.beautylab.ui.widgets.BLCaption
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLFab
import com.nek12.beautylab.ui.widgets.BLSpacer
import com.nek12.beautylab.ui.widgets.BLTextInput
import com.nek12.beautylab.ui.widgets.ChipFlowRow
import com.nek12.beautylab.ui.widgets.SliderWithLabel
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun ProductFiltersScreen(
    initialFilters: FiltersPayload,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<FiltersPayload>,
) = MVIComposable(getViewModel<ProductFiltersViewModel> { parametersOf(initialFilters) }) { state ->

    val dialogState = rememberMaterialDialogState()
    MaterialDialog(dialogState) {
        datepicker(
            title = R.string.select_date.string(),
            yearRange = 2020..2030,
            allowedDateValidator = { it.isBefore(LocalDate.now()) },
            onDateChange = { send(ProductFiltersIntent.SelectedDateCreated(it)) }
        )

    }

    consume { action ->
        when (action) {
            is GoBack -> resultNavigator.navigateBack(action.filters)
            is OpenDateCreatedPicker -> dialogState.show()
        }
    }

    ProductFiltersContent(state)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MVIIntentScope<ProductFiltersIntent, ProductFiltersAction>.ProductFiltersContent(state: ProductFiltersState) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .heightIn(min = 400.dp)
    ) {
        when (state) {
            is Error -> BLErrorView(state.e.genericMessage.string())
            Loading -> CircularProgressIndicator()
            is SelectingFilters -> {
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    BLCaption(R.string.brands)
                    ChipFlowRow(chips = state.brands,
                        contentSelector = { it.name },
                        isSelected = { it == state.selectedBrand },
                        onClick = { send(ProductFiltersIntent.ClickedBrand(it)) }
                    )
                    BLSpacer()

                    BLCaption(R.string.categories)
                    ChipFlowRow(chips = state.categories,
                        contentSelector = { it.name },
                        isSelected = { it == state.selectedCategory },
                        onClick = { send(ProductFiltersIntent.ClickedCategory(it)) })
                    BLSpacer()

                    //TODO: Proper widget
                    BLCaption(R.string.sort_by)
                    val sortValues = remember { ProductSort.values().toList() }
                    ChipFlowRow(
                        chips = sortValues,
                        contentSelector = { it.representation() },
                        isSelected = { it == state.sort },
                        onClick = { send(ProductFiltersIntent.SelectedSort(it)) }
                    )
                    BLSpacer()

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(R.string.is_sort_ascending.string())
                        Switch(state.isAscending, { send(ProductFiltersIntent.SwitchedAscending(it)) })
                    }

                    BLCaption(R.string.discount_template.string(state.discountFrom, state.discountTo))
                    RangeSlider(
                        modifier = Modifier.fillMaxWidth(),
                        values = state.discountRange,
                        onValueChange = { send(ProductFiltersIntent.ChangedDiscount(it)) },
                        valueRange = 0f..1f
                    )
                    BLSpacer()

                    BLCaption(R.string.price)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BLTextInput(
                            input = state.minPrice,
                            modifier = Modifier
                                .padding(4.dp)
                                .requiredHeightIn(min = 64.dp)
                                .weight(0.5f, true),
                            onTextChange = { send(ProductFiltersIntent.ChangedMinPrice(it)) },
                            label = R.string.min_price.string(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                        )

                        BLTextInput(
                            input = state.maxPrice, modifier = Modifier
                                .padding(4.dp)
                                .requiredHeightIn(min = 64.dp)
                                .weight(0.5f, true),
                            onTextChange = { send(ProductFiltersIntent.ChangedMaxPrice(it)) },
                            label = R.string.max_price.string(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )

                    }
                    BLSpacer()


                    BLCaption(R.string.mininum_amount_available)
                    SliderWithLabel(
                        value = state.minAmountAvailable,
                        onValueChange = { send(ProductFiltersIntent.ChangedMinAmountAvailable(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        valueRange = ProductFiltersState.PRODUCT_AMOUNT_RANGE,
                        finiteEnd = true
                    )

                    BLSpacer()
                    Button(onClick = { send(ProductFiltersIntent.ClickedSelectDateCreated) }) {
                        Text(R.string.select_created_after.string(), style = MaterialTheme.typography.caption)
                    }
                    BLSpacer()

                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BLFab(
                            icon = GMRIcon.gmr_clear,
                            onClick = { send(ProductFiltersIntent.ClickedReset) },
                            backgroundColor = MaterialTheme.colors.error
                        )

                        BLFab(
                            icon = GMRIcon.gmr_check,
                            onClick = { send(ProductFiltersIntent.ClickedOk) },
                            backgroundColor = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "ProductFilters", showSystemUi = true, showBackground = true)
private fun ProductFiltersPreview() = ScreenPreview {
    Column {
        ProductFiltersContent(state = Error(Exception()))
    }
}
