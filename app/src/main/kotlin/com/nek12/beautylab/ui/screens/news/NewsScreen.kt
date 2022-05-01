package com.nek12.beautylab.ui.screens.news

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.nek12.androidutils.compose.string
import com.nek12.beautylab.R
import com.nek12.beautylab.common.ScreenPreview
import com.nek12.beautylab.common.genericMessage
import com.nek12.beautylab.ui.items.NewsCardItem
import com.nek12.beautylab.ui.screens.news.NewsAction.GoBack
import com.nek12.beautylab.ui.screens.news.NewsState.DisplayingNews
import com.nek12.beautylab.ui.screens.news.NewsState.Error
import com.nek12.beautylab.ui.screens.news.NewsState.Loading
import com.nek12.beautylab.ui.widgets.BLBottomBar
import com.nek12.beautylab.ui.widgets.BLErrorView
import com.nek12.beautylab.ui.widgets.BLTopBar
import com.nek12.flowMVI.android.compose.MVIComposable
import com.nek12.flowMVI.android.compose.MVIIntentScope
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.getViewModel

@Composable
@Destination
fun NewsScreen(
    navController: NavController,
) = MVIComposable(getViewModel<NewsViewModel>()) { state ->

    val scaffoldState = rememberScaffoldState()

    consume { action ->
        when (action) {
            is GoBack -> navController.navigateUp()
        }
    }

    NewsContent(state, navController, scaffoldState)
}

@Composable
private fun MVIIntentScope<NewsIntent, NewsAction>.NewsContent(
    state: NewsState,
    navController: NavController,
    scaffoldState: ScaffoldState,
) {
    when (state) {
        is Error -> BLErrorView(state.e.genericMessage.string())
        is Loading -> Unit
        is DisplayingNews -> {
            val items = state.data.collectAsLazyPagingItems()

            Scaffold(
                scaffoldState = scaffoldState,
                topBar = { BLTopBar(R.string.news) },
                bottomBar = { BLBottomBar(navController) }
            ) {
                LazyColumn(Modifier.padding(it)) {
                    items(items, key = { it.id }) { item ->
                        item?.let {
                            NewsCardItem(item = item, onClick = {}) //do nothing
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(name = "News", showSystemUi = true, showBackground = true)
private fun NewsPreview() = ScreenPreview {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    Column {
        NewsContent(state = Error(Exception()), navController, scaffoldState)
    }
}
