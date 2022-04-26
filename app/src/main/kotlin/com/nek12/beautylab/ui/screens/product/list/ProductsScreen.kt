package com.nek12.beautylab.ui.screens.product.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.nek12.beautylab.common.FiltersPayload
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductsScreen(
    filters: FiltersPayload? = null,
    navigator: DestinationsNavigator,
) {

}


@Composable
@Preview(name = "ProductsScreen", showSystemUi = false, showBackground = true)
private fun ProductsScreenPreview() {

}
