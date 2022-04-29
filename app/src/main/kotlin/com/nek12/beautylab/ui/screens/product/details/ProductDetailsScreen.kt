package com.nek12.beautylab.ui.screens.product.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.annotatedPrice
import com.nek12.beautylab.ui.screens.destinations.OrderConfirmationScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductListScreenDestination
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.GoBack
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.GoToOrderConfirmation
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsAction.GoToProductsList
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedBrand
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedBuy
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedCategory
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsIntent.ClickedFavorite
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.DisplayingProduct
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.Empty
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.Error
import com.nek12.beautylab.ui.screens.product.details.ProductDetailsState.Loading
import com.nek12.beautylab.ui.widgets.BLCircleIcon
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLFab
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

@Composable
@Destination
fun ProductDetailsScreen(
    id: UUID,
    navigator: DestinationsNavigator,
) = MVIComposable(getViewModel<ProductDetailsViewModel> { parametersOf(id) }) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is GoBack -> navigator.navigateUp()
            is GoToOrderConfirmation -> navigator.navigate(OrderConfirmationScreenDestination(action.id))
            is GoToProductsList -> navigator.navigate(ProductListScreenDestination(action.filters))
        }
    }

    ProductDetailsContent(state, scaffoldState)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MVIIntentScope<ProductDetailsIntent, ProductDetailsAction>.ProductDetailsContent(
    state: ProductDetailsState,
    scaffoldState: ScaffoldState,
) {
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = { BLFab(icon = GMRIcon.gmr_shopping_cart, onClick = { send(ClickedBuy) }) }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .heightIn(min = 400.dp),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is Empty -> BLEmptyView()
                is Error -> BLErrorView(state.e.genericMessage.string())
                is Loading -> CircularProgressIndicator()
                is DisplayingProduct -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(bottom = 64.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Text(
                            text = state.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Center,
                            softWrap = true,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Card(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            AsyncImage(
                                model = state.imageUrl,
                                contentDescription = R.string.product_image_cd.string(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 240.dp, min = 160.dp),
                                contentScale = ContentScale.Crop,
                            )

                            if (state.isFavoriteLoading) {
                                CircularProgressIndicator()
                            } else {
                                val color =
                                    if (state.isFavorite) MaterialTheme.colors.error else MaterialTheme.colors.primary
                                val icon =
                                    if (state.isFavorite) GMRIcon.gmr_favorite else GMRIcon.gmr_favorite_outline
                                BLCircleIcon(
                                    icon = icon,
                                    color = color,
                                    size = 44.dp,
                                    onClick = { send(ClickedFavorite) },
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .then(with(this@Box) { Modifier.align(Alignment.BottomEnd) })

                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {

                            //left block
                            Column(Modifier.weight(0.6f)) {
                                //price
                                Text(
                                    text = annotatedPrice(
                                        price = state.price,
                                        priceWithDiscount = state.priceWithDiscount
                                    ),
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    style = MaterialTheme.typography.h6,
                                    softWrap = false,
                                    textAlign = TextAlign.Start,
                                )

                                //amount
                                Text(
                                    text = R.string.amount_template.string(state.amountAvailable),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.body1,
                                    softWrap = true,
                                )
                            }

                            //right block
                            Column(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.End
                            ) {
                                Chip(
                                    onClick = { send(ClickedBrand) },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        state.brand.name,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                Chip(
                                    onClick = { send(ClickedCategory) },
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                ) {
                                    Text(
                                        state.category.name,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        Divider(Modifier.padding(vertical = 4.dp))


                        //description
                        Text(
                            text = R.string.description.string(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            style = MaterialTheme.typography.h5,
                            textAlign = TextAlign.Start,
                            softWrap = false,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                        ) {
                            Text(
                                text = state.description,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                            )
                        }
                        //properties
                        state.properties?.let {

                            Divider(Modifier.padding(vertical = 4.dp))

                            Text(
                                text = R.string.properties.string(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                style = MaterialTheme.typography.h5,
                                textAlign = TextAlign.Start,
                                softWrap = false,
                            )

                            Text(
                                text = state.properties,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Start,
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
@Preview(name = "ProductDetails", showSystemUi = false, showBackground = true)
private fun ProductDetailsPreview() = ScreenPreview(false) {

    ProductDetailsContent(
        state = DisplayingProduct(UUID.randomUUID(), Mock.product).copy(amountSelected = 14),
        rememberScaffoldState()
    )
}
