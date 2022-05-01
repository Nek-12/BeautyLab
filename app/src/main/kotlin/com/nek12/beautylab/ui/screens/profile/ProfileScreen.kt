package com.nek12.beautylab.ui.screens.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nek12.androidutils.compose.string
import com.nek12.androidutils.extensions.core.copies
import com.nek12.beautylab.R
import com.nek12.beautylab.common.GMRIcon
import com.nek12.beautylab.common.Mock
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.TransactionItem
import com.nek12.beautylab.ui.screens.destinations.CancelOrderScreenDestination
import com.nek12.beautylab.ui.screens.destinations.OrderHistoryScreenDestination
import com.nek12.beautylab.ui.screens.destinations.ProductDetailsScreenDestination
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoBack
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToCancelOrder
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToOrderHistory
import com.nek12.beautylab.ui.screens.profile.ProfileAction.GoToProductDetails
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedCancelOrder
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedOrder
import com.nek12.beautylab.ui.screens.profile.ProfileIntent.ClickedSeeHistory
import com.nek12.beautylab.ui.screens.profile.ProfileState.DisplayingProfile
import com.nek12.beautylab.ui.screens.profile.ProfileState.Empty
import com.nek12.beautylab.ui.screens.profile.ProfileState.Error
import com.nek12.beautylab.ui.screens.profile.ProfileState.Loading
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLCircleIcon
import com.nek12.beautylab.ui.widgets.BLEmptyView
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLIcon
import com.nek12.beautylab.ui.widgets.BLUserProfileCard
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun ProfileScreen(
    navController: NavController,
) = MVIComposable(getViewModel<ProfileViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is GoBack -> navController.navigateUp()
            is GoToCancelOrder -> navController.navigateTo(CancelOrderScreenDestination(action.id))
            is GoToOrderHistory -> navController.navigateTo(OrderHistoryScreenDestination)
            is GoToProductDetails -> navController.navigateTo(ProductDetailsScreenDestination(action.productId))
        }
    }

    ProfileContent(state, navController, scaffoldState)
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun MVIIntentScope<ProfileIntent, ProfileAction>.ProfileContent(
    state: ProfileState,
    navController: NavController,
    scaffoldState: ScaffoldState
) {
    Scaffold(
        bottomBar = { BLBottomBar(navController = navController) },
        scaffoldState = scaffoldState,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(4.dp), contentAlignment = Alignment.Center
        ) {
            when (state) {
                is Empty -> BLEmptyView()
                is Error -> BLErrorView(state.e.genericMessage.string())
                is Loading -> CircularProgressIndicator()
                is DisplayingProfile -> {
                    Column(Modifier.fillMaxSize()) {

                        BLUserProfileCard(
                            name = state.name,
                            balance = state.bonusBalance,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        )

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            onClick = { send(ClickedSeeHistory) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                BLCircleIcon(
                                    icon = GMRIcon.gmr_history,
                                    color = MaterialTheme.colors.primary,
                                    modifier = Modifier.padding(8.dp)
                                )

                                Text(
                                    R.string.order_history.string(),
                                    Modifier.padding(8.dp),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )

                                BLIcon(asset = GMRIcon.gmr_arrow_forward_ios, Modifier.padding(8.dp))
                            }
                        }

                        Text(
                            text = R.string.pending_orders_profile_subtitle.string(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5,
                        )

                        LazyColumn {
                            items(state.pendingTransactions, key = { it.id }) { item ->
                                TransactionItem(
                                    item = item,
                                    onClick = { send(ClickedOrder(item)) },
                                    onActionClicked = { send(ClickedCancelOrder(item)) },
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .animateItemPlacement()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "Profile", showSystemUi = true, showBackground = true)
private fun ProfilePreview() = ScreenPreview {
    ProfileContent(
        DisplayingProfile(
            name = "Nek.12", bonusBalance = 99999.9,
            pendingTransactions = TransactionItem(Mock.transaction).copies(10),
        ),
        rememberNavController(),
        rememberScaffoldState()
    )
}
